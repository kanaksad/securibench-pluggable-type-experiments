import re
from collections import defaultdict

log_text = """
	- Collections:
         TP: 
         TP2:
		 FN: 14(14)
		 TN: 7(7)
    - Aliasing:
         TP: 1, 3, 4(2), 6(7)
		 TP2: 5
		 TN: 2, 4
	- Arrays:
		 TP: 1, 2, 3, 4, 6, 7, 8, 9, 10
		 TN: 2(2), 3, 5, 8, 10
		 FP: 5, 10
	 - Basic: 
		 TP: 1, 2, 3, 4, 5(3), 6, 7, 8, 9, 10, 11(2), 12(2), 13, 15, 16, 17, 18, 19, 20, 21(4), 22, 23(3), 24, 25, 27, 28(2), 29(2), 30, 32, 33, 34, 37, 38, 
		 TP2: 31(3), 36, 40, 
		 TN: 11, 12, 17, 29, 30, 38
		 FN: 14, 34, 35(6), 39, 41, 42
	 - DataStructures: 
		 TP: 1, 2, 3, 5, 6
		 TN: 1, 2,
		 FP: 4
	 - Factories: 
		 TP: 1, 2, 3
		 TN: 1, 2, 3
	 - Inter:
		 TP: 1, 2, 3, 4, 5, 6, 7, 8, 9(2), 10, 11, 13, 14
		 TN: 1, 2, 3(2), 5, 8, 9(2), 10, 11, 12
		 TP2: 1
		 FN: 12
	- Pred:
		TP: 2, 4, 5, 8, 9
		FP: 1, 3, 6, 7
	- Refl: 
		TP: 1, 3, 4
		FN: 2
	 - Sanitizers:
		 TP: 1, 4
		 FN: 4
		 TN: 1, 2
		 TP2: 1
		 FP: 2, 3, 5, 6
	 - Session:
		 TP: 1, 2, 3
		 FP: 2
	- StrongUpdates:
		TP: 4
		FP: 3, 5
		TN: 1, 2 
"""

# Patterns
category_pattern = re.compile(r'^-+\s*(\w+)')
entry_pattern = re.compile(r'(\d+)(?:\((\d+)\))?')  # capture base number and optional (X)

categories = defaultdict(lambda: {"TP":0,"TP2":0,"FP":0,"FN":0,"TN":0})
global_totals = {"TP":0, "TP2":0, "FP":0, "FN":0, "TN":0}

current_category = None

for line in log_text.split('\n'):
    raw_line = line
    line = line.strip()
    if not line:
        continue

    # Check for category line
    cat_match = category_pattern.match(line)
    if cat_match:
        current_category = cat_match.group(1)
        continue
    
    if current_category is None:
        continue

    # Remove leading dashes and spaces before checking for TP, TP2, etc.
    # Some lines like "- FN: 14"
    stripped_line = raw_line.lstrip('- ').strip()

    for prefix in ["TP:", "TP2:", "FP:", "FN:", "TN:"]:
        if stripped_line.startswith(prefix):
            cat = prefix[:-1]
            after_colon = stripped_line[len(prefix):].strip()
            if not after_colon:
                # no entries on this line
                continue
            
            # Split by comma
            entries = [e.strip() for e in after_colon.split(',') if e.strip()]
            for entry in entries:
                # Remove trailing '?' if present
                if entry.endswith('?'):
                    entry = entry[:-1]
                # Match with regex
                match = entry_pattern.search(entry)
                if match:
                    count_str = match.group(2)
                    count = int(count_str) if count_str else 1
                    categories[current_category][cat] += count
                    global_totals[cat] += count
            break

# Print summary per category
print("=== Summary per Category ===")
category_counts = 0
for cat_name in sorted(categories.keys()):
    cat_data = categories[cat_name]
    c_total = sum(cat_data.values())
    category_counts += c_total
    print(f"{cat_name}: TP={cat_data['TP']}, TP2={cat_data['TP2']}, FP={cat_data['FP']}, FN={cat_data['FN']}, TN={cat_data['TN']} (Total: {c_total})")

print("\n=== Global Totals ===")
total_all = 0
for k in ["TP", "TP2", "FP", "FN", "TN"]:
    total_all += global_totals[k]
    print(f"{k}: {global_totals[k]}")

print(f"\nGrand Total: {total_all}")
