from typing import List

#1: Input Filter
def input_filter(text: str) -> List[str]:
    return text.split()

#2: Keyword Finder Filter
def keyword_finder_filter(words: List[str], keyword: str) -> List[int]:
    return [i for i, word in enumerate(words) if word == keyword]

# Step 3: Context Extractor Filter - Gets context around each keyword occurrence
def context_extractor_filter(words: List[str], positions: List[int], context_size: int) -> List[str]:
    contexts = []
    for pos in positions:
        start = max(0, pos - context_size)
        end = min(len(words), pos + context_size + 1)
        contexts.append(' '.join(words[start:end]))
    return contexts

#: Sorter Filter
def sorter_filter(contexts: List[str]) -> List[str]:
    return sorted(contexts)

#5: Display Filter
def display_filter(contexts: List[str]):
    for context in contexts:
        print(context)

# put all function
def kwic_pipeline(text: str, keyword: str, context_size: int):
    words = input_filter(text)
    positions = keyword_finder_filter(words, keyword)
    contexts = context_extractor_filter(words, positions, context_size)
    sorted_contexts = sorter_filter(contexts)
    display_filter(sorted_contexts)

# implementation code
text = "Winter is coming. The snow is falling. Let's go ice skating in the winter. The winter wind is cold and biting."
keyword = "winter"
context_size = 2
kwic_pipeline(text, keyword, context_size)
