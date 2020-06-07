'''
Функции получения некоторых значений из данных
'''

# Получение значения самого популярного слова
def getTheMostPopularWord(sentenceWords):
  answerWord = "";
  answerNumberOfMeet = 0;

  for i in range(len(sentenceWords)):

    tempWord = sentenceWords[i];
    tempNumberOfMeet = 0;

    for j in range(len(sentenceWords)):
      if (i != j and tempWord == sentenceWords[j]):
        tempNumberOfMeet += 1;
    
    if (tempNumberOfMeet > answerNumberOfMeet):
      answerNumberOfMeet = tempNumberOfMeet;
      answerWord = tempWord;
    
    elif (tempNumberOfMeet == answerNumberOfMeet and len(tempWord) > len(answerWord)):
      answerNumberOfMeet = tempNumberOfMeet;
      answerWord = tempWord;
    
  return answerWord;

