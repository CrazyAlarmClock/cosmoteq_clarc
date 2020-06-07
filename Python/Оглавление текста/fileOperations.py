'''
  Функции обработки ввода файла
'''

import globalNames;

# Считывание текста из файла 
def readInputText(fileInName):
  try:
    #global inputFile, inputText;
    globalNames.inputFile = open(fileInName, "r");
    globalNames.inputText = globalNames.inputFile.read().replace("...", ".").replace("..", ".");
    return False;
  except:
    print("Input file doesn't exist!");
    return True;

# Создание выходных данных и файла
def createOutputFile(errorflag, fileOutName):
  #global outputText, outputFile;
  if (errorflag == False):
    globalNames.outputFile = open(fileOutName, "w");

# Окончание программы: сохранение полученных данных и закрытие модулей
def saveData(errorflag):
  if (errorflag == False):
    globalNames.outputFile.write(globalNames.outputText);

  globalNames.inputFile.close();
  #globalNames.outputFile.close();

