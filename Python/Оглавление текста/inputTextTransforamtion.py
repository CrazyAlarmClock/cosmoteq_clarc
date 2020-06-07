import globalNames, validationFunctions, globalConstants;

# Получение предложений из текста и слов в каждом предложении
def getSentences(errorflag):
  allTempSentences = []
  allTempSentencesWords = []

  if (errorflag == False):
    tempSentence = "";
    for i in range(len(globalNames.inputText)):
      if (validationFunctions.isEndSentenceSymbols(globalNames.inputText[i]) == True):
        tempSentence += globalNames.inputText[i];
        if (len(tempSentence) > 0):
          allTempSentences.append([[i], globalConstants.nameOfParagraph, tempSentence]);
          allTempSentencesWords.append([[i], globalConstants.nameOfParagraph, tempSentence.split()]);
        tempSentence = "";
      else:
        tempSentence += globalNames.inputText[i];

  if (len(allTempSentences) > 0):
    globalNames.textSentences = allTempSentences;
    globalNames.textSentencesWords = allTempSentencesWords;

# Получение предложений свободных от вводных слов
def getSentencesFreeIntroductoryWords(errorflag):
  tempTextSentencesFree = []

  if (errorflag == False):
    for i in range(len(globalNames.textSentencesWords)):
      tempWordsFree = [];
      for j in range(len(globalNames.textSentencesWords[i][2])):

        if (validationFunctions.isSubstrIntroductoryWord(globalNames.textSentencesWords[i][2][j]) == False and validationFunctions.isSizeOk(globalNames.textSentencesWords[i][2][j]) == True):
          tempWordsFree.append(globalNames.textSentencesWords[i][2][j]);
      tempTextSentencesFree.append([[i], globalNames.textSentencesWords[i][2][j], tempWordsFree]);
  print("len(tempTextSentencesFree) = " + str(len(tempTextSentencesFree)))
  if (len(tempTextSentencesFree) > 0):
    globalNames.textSentencesFree = tempTextSentencesFree;





