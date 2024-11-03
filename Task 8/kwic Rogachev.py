# класс для сохранения инпута
class input_mech:
    def __init__(self, storage):
        self.storage = storage

    def read_input(self):
        # Здесь надо вводить инпут
        lines = [
            "software architecture is awful",
            "Sonya the best cat in the world"
        ]
        for line in lines:
            self.storage.add_original_line(line)


# класс для хранения строк данных
class line_storage:
    def __init__(self):
        self.original_lines = []
        self.shifted_lines = []

    def add_original_line(self, line):
        self.original_lines.append(line)

    def add_shifted_line(self, line):
        self.shifted_lines.append(line)

    def get_original_lines(self):
        return self.original_lines

    def get_shifted_lines(self):
        return self.shifted_lines


# класс для сдвига исходных строк
class line_shift:
    def __init__(self, storage):
        self.storage = storage

    def make_shift(self):
        for line in self.storage.get_original_lines():
            words = line.split()
            for i in range(len(words)):
                shifted_line = ' '.join(words[i:] + words[:i])
                self.storage.add_shifted_line(shifted_line)


# класс для сортировки (по умолчанию по алфавиту)
class sorter:
    def __init__(self, storage):
        self.storage = storage
        self.sorted_lines = []

    def sort_lines(self):
        self.sorted_lines = sorted(self.storage.get_shifted_lines(), key=lambda s: s.lower())

    def get_sorted_lines(self):
        return self.sorted_lines


# класс для вывода? пересмотреть, мб можно объеденить с инпутом
class output_mech:
    def __init__(self, alphabetizer):
        self.alphabetizer = alphabetizer

    def write_output(self):
        for line in self.alphabetizer.get_sorted_lines():
            print(line)



class KWIC:
    def __init__(self):
        self.storage = line_storage()
        self.input_mech = input_mech(self.storage)
        self.line_shift = line_shift(self.storage)
        self.sorter = sorter(self.storage)
        self.output_mech = output_mech(self.sorter)

    def execute(self):
        self.input_mech.read_input()
        self.line_shift.make_shift()
        self.sorter.sort_lines()
        self.output_mech.write_output()


start = KWIC()
start.execute()
