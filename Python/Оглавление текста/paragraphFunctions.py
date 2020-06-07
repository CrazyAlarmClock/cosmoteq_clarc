import globalNames, validationFunctions, getFunctions;

# Формирование полученных абазацев по контенту
def getTableOfContents(errorflag):
  globalNames.outputText += "Оглавление\n";
  for i in range(len(globalNames.textSentencesFree)):
    tempString = str(i) + ". " + str(globalNames.textSentencesFree[i][1]) + "\n";
    globalNames.outputText += tempString;

  globalNames.outputText += "\nНазвание текста\n";
  for i in range(len(globalNames.textSentencesFree)):
    tempString = ""
    tempString += "\t"
    for j in range(len(globalNames.textSentencesFree[i][0])):
      indexes = globalNames.textSentencesFree[i][0];
      #print(indexes[j]);
      
      tempString += globalNames.textSentences[indexes[j]][2];
      
      #tempString = "\t" + globalNames.textSentences[globalNames.textSentencesFree[i][0][j]][2] + "\n";
    tempString += "\n";
    globalNames.outputText += tempString;
  
  globalNames.outputFile.write(globalNames.outputText);

# Группирвоание предложений по контекстной нагрузке
def groupSentence(errorflag):
  if (errorflag == False):
    tempTextSentenceFree = [];
    position = validationFunctions.isOpportunityToGroup()
    if (position != False):
      i = 0;
      while i != len(globalNames.textSentencesFree) - 1:
        if (i == position):
          indexes = globalNames.textSentencesFree[i][0] + globalNames.textSentencesFree[i + 1][0] + globalNames.textSentencesFree[i + 2][0]
          words = globalNames.textSentencesFree[i][2] + globalNames.textSentencesFree[i + 1][2] + globalNames.textSentencesFree[i + 2][2];
          name = getFunctions.getTheMostPopularWord(words);
          tempTextSentenceFree.append([indexes, name, words]);
          i += 3;
          #print(tempTextSentenceFree, end = "\n");
        else:
          tempTextSentenceFree.append(globalNames.textSentencesFree[i]);
          i += 1;
      globalNames.textSentencesFree = tempTextSentenceFree;
      groupSentence(errorflag);
      #return False;

def trossSentencesFree(errorflag):
  if (errorflag == False):
    tempTextSentenceFree = [];
    i = 0;
    tempSentence = [[], "name", []];
    while i != len(globalNames.textSentencesFree) - 1:
      tempSentence = [tempSentence[0] + globalNames.textSentencesFree[i][0], "name", tempSentence[2] + globalNames.textSentencesFree[i][2]]
      #print(tempSentence);
      if (len(globalNames.textSentencesFree[i][0]) != 1 or i == len(globalNames.textSentencesFree) - 2 or len(tempSentence[0]) > 200):
        name = getFunctions.getTheMostPopularWord(tempSentence[2]);
        tempSentence[1] = name;
        #print(tempSentence);
        tempTextSentenceFree.append(tempSentence);
        tempSentence = [[], "name", []];
      i += 1;
    if (len(tempSentence[0]) != 0):
      name = getFunctions.getTheMostPopularWord(tempSentence[2]);
      tempSentence[1] = name;
      #print(tempSentence);
      tempTextSentenceFree.append(tempSentence);
      tempSentence = [[], "name", []];
    globalNames.textSentencesFree = tempTextSentenceFree;