class KWIC:
    def __init__(self, text, keyword, size=30):
        self.text = text
        self.keyword = keyword
        self.size = size
        self.sentences = self.text.split('. ')

    def find_keyword_contexts(self):
        contexts = []
        keyword_lower = self.keyword.lower()

        for i, sentence in enumerate(self.sentences):
            if keyword_lower in sentence.lower():
                current_context = sentence.strip()
                j = i + 1
                while j < len(self.sentences) and len(current_context) + len(self.sentences[j]) < self.size:
                    current_context += '. ' + self.sentences[j].strip()
                    j += 1

                contexts.append(current_context)
        
        return contexts

    def print_contexts(self):
        contexts = self.find_keyword_contexts()
        if not contexts:
            print("/n")
            print(f"No occurrences of '{self.keyword}' found.")
        else:
            print("/n")
            for idx, context in enumerate(contexts, 1):
                print(f"{idx}. ... {context} ...")

# text = """
# Software engineering is an engineering approach to software development. A practitioner, called a software engineer, applies the engineering design process to develop software. The terms programmer and coder overlap software engineer, but they imply only the construction aspect of typical software engineer workload. A software engineer applies a software development process, which involves defining, implementing, testing, managing, and maintaining software systems and, creating and modifying the development process.
# """
# keyword = "software"
text = input("Inpute text: ")

keyword = input("Input keyword: ")

size = int(input("Input size: "))

kwic_instance = KWIC(text, keyword, size)
kwic_instance.print_contexts()
