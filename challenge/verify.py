#!/usr/bin/env python3
"""Verify the expected filesystem output (not JMS transactional guarantees)."""
import json
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parent / 'data'
EXPECTED = {
    'BER': {'P001', 'C003', 'P004'},
    'MAD': {'C001', 'C002', 'P003'},
    'LIS': {'P002', 'C004'},
}
TRANSFER = {'P002', 'C002', 'C004', 'P004'}
ALL = set().union(*EXPECTED.values())

def read_files(folder):
    files = list(folder.glob('*.json'))
    data = [json.loads(f.read_text(encoding='utf-8')) for f in files]
    ids = [item['id'] for item in data]
    return files, ids, data

problems = []
all_arrivals = []
for destination, expected_ids in EXPECTED.items():
    files, ids, records = read_files(ROOT / 'arrivals' / destination)
    all_arrivals.extend(ids)
    if set(ids) != expected_ids or len(ids) != len(expected_ids):
        problems.append(f'{destination}: found {sorted(ids)}; expected {sorted(expected_ids)}')
    if any(r.get('destination') != destination for r in records):
        problems.append(f'{destination}: destination field mismatch')

files, transfers, _ = read_files(ROOT / 'transfers' / 'MUC')
if set(transfers) != TRANSFER or len(transfers) != len(TRANSFER):
    problems.append(f'MUC transfers: found {sorted(transfers)}; expected {sorted(TRANSFER)}')

files, audit, _ = read_files(ROOT / 'audit')
if set(audit) != ALL or len(audit) != len(ALL):
    problems.append(f'audit: found {sorted(audit)}; expected {sorted(ALL)}')

if len(all_arrivals) != 8 or any(count != 1 for count in Counter(all_arrivals).values()):
    problems.append(f'arrivals contain duplicates/missing items: {all_arrivals}')

if problems:
    print('NOT YET COMPLETE')
    for issue in problems:
        print(' -', issue)
    raise SystemExit(1)
print('OK: 8 unique arrivals, 4 transfers via MUC, 8 audit entries.')
