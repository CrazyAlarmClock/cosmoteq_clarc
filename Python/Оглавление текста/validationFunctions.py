import globalNames, globalConstants, getFunctions;

# Проверка символа на окончание предложения
def isEndSentenceSymbols(letter):
  errorflag = False;
  for i in range(len(globalNames.endSentenceSymbols)):
    if (globalNames.endSentenceSymbols[i] == letter):
      errorflag = True;
      break;
  return errorflag;

# Проверка слова на наличие вводного слова
def isSubstrIntroductoryWord(wordToCheck):
  wordToCheck = wordToCheck.lower();
  errorflag = False;
  for i in range(len(globalNames.introductoryWords)):
    if (wordToCheck.count(globalNames.introductoryWords[i]) > 0):
      errorflag = True;
      break;
  return errorflag;

# Сравнение размера слова с минимально допустимым
def isSizeOk(tempword):
  if (len(tempword) <= globalConstants.sizeOfWord):
    return False;
  return True;

# Проверка возможности группировки предложений в тексте
def isOpportunityToGroup():
  for i in range(len(globalNames.textSentencesFree) - 2):
    words1 = globalNames.textSentencesFree[i + 0][2]
    words2 = globalNames.textSentencesFree[i + 1][2]
    words3 = globalNames.textSentencesFree[i + 2][2]

    flag1_2 = 0;
    flag1_3 = 0;
    flag2_3 = 0;

    for x1 in (words1):
      for x2 in (words2):
        for x3 in (words3):
          if (x1.count(x2) > 0 or x2.count(x1) > 0):
            flag1_2 += 1;
          if (x1.count(x3) > 0 or x3.count(x1) > 0):
            flag1_3 += 1;
          if (x2.count(x3) > 0 or x3.count(x2) > 0):
            flag2_3 += 1;

    if (flag1_2 + flag1_3 + flag2_3 > 0):
      return i;

  return False;