'''
COSMOTEQ
6.06.2020
Test 1: in_1.txt out_1.txt
'''

import globalNames, fileOperations, inputTextTransforamtion, paragraphFunctions;

# Основная функция программы main(file_in.txt, file_out.txt)
def main(fileInName, fileOutName):
  # Чтение текстового файла
  errorflag = fileOperations.readInputText(fileInName);

  # Создание выходного файла и настройка
  fileOperations.createOutputFile(errorflag, fileOutName);

  # Получение предложений из текста
  inputTextTransforamtion.getSentences(errorflag)

  # Получение предложений из слов без вводных
  inputTextTransforamtion.getSentencesFreeIntroductoryWords(errorflag);
  
  # Формирование по группам предложений 
  paragraphFunctions.groupSentence(errorflag)

  # Троссировка групп предложений
  paragraphFunctions.trossSentencesFree(errorflag)
  
  # Формирование вывода
  paragraphFunctions.getTableOfContents(errorflag)

  # Непосредственный вывод в файл
  #fileOperations.saveData(errorflag);

  tempFileOut = open(fileOutName, "w");
  #tempFileOut.write(globalNames.outputText);
  tempFileOut.close()
  globalNames.inputFile.close();

# Ввод параметров и запуск программы
fileInName, fileOutName = input("Input fileInName fileOutName: ").split();
main(fileInName, fileOutName);

#print(globalNames.outputText);
#print(globalNames.textSentencesFree, sep = "\n");