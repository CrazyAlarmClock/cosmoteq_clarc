package com.clarc.main;

import java.io.*;
import java.nio.file.*;

public class InfopageCreateController {
	
	public static String[] titleWords = {"Наименование", "наименование", "документ", "инструкция", "ИНСТРУКЦИЯ"};
	public static String[] pointWords = {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "1)", "2)", "3)", "4)", "5)", "6)", "7)", "8)", "9)", "а.", "б.", "в.", "г.", "д.", "е.", "ё.", "ж.", "з.", "и.", "к.", "л.", "м.", "н.", "о.", "п.", "р.", "с.", "т.", "у.", "ф.", "х.", "ц.", "ч.", "ш.", "щ.", "ъ.", "ы.", "ь.", "э.", "ю.", "я.", "а)", "б)", "в)", "г)", "д)", "е)", "ё)", "ж)", "з)", "и)", "к)", "л)", "м)", "н)", "о)", "п)", "р)", "с)", "т)", "у)", "ф)", "х)", "ц)", "ч)", "ш)", "щ)", "ъ)", "ы)", "ь)", "э)", "ю)", "я)", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static String[] paragraphWords = {"Общие", "общие", "Общее", "общее", "положение", "положения", "должностные обязанности", "Должностные обязанности", "Права", "Права и обязанности", "права", "права и обязанности", "Ответственность", "ответственность"};

	public static String readFileAsString(String fileName) throws Exception 
	{ 
	    String data = new String(Files.readAllBytes(Paths.get(fileName)));

	    return data; 
	} 

	public float createPage(String fileName, long fileId)
	{
	   float finalScore = 1;
	   
	   String rawText = "";
	   String pageTemplate = "";
	   String logData = "";

	   try {
		   rawText = readFileAsString("/Users/pavelandreyev/Documents/workspace-sts-3.9.9.RELEASE/DeepIndex/src/main/resources/static/"+fileName);	   
	   } catch(Exception e) {}

	   // System.out.println(rawText);
	   
	   String[] textParts = rawText.split("\n");
	   
	   System.out.println(textParts[0]);
	   logData += textParts[0]+"\n";

	   try {
		   pageTemplate = readFileAsString("/Users/pavelandreyev/Documents/workspace-sts-3.9.9.RELEASE/Clarc/src/main/resources/static/infopages/template_infopage.html");	   
	   } catch(Exception e) {}

	   String pageTitle = findTitle(textParts);
	   
	   System.out.println(pageTitle);
	   logData += pageTitle+"\n";

	   String[] paragraphTitles = new String[100];
	   String[] pointsTxt = new String[100];
	   
	   int indx = 0;

	   for(int f = 0; f < 5; f++)
	   {
		   String[] paragraphData = findParagraphTitle(textParts, indx);

		   if(paragraphData[0].length() > 0)
		   {
			   System.out.println(paragraphData.length);
			   System.out.println(paragraphData[0]);
			   System.out.println(paragraphData[1]);
			   logData += paragraphData.length+"\n";
			   logData += paragraphData[0]+"\n";
			   logData += paragraphData[1]+"\n";

			   indx = Integer.parseInt(paragraphData[1])+1;
			   paragraphTitles[f] = paragraphData[0];
			   
			   String[] paragraphDataEnd = findParagraphTitle(textParts, indx);
			   int endIndx = textParts.length;

			   if(paragraphDataEnd[0].length() > 0)
			   {
				   endIndx = Integer.parseInt(paragraphDataEnd[1]);
			   }
			   
			   String[] paragraph = cutParagraph(textParts, indx, endIndx);
			   
			   String[] pointsFound = findPoints(paragraph);

			   System.out.println(paragraphTitles[f]);
			   
			   String tmpConcat = "";

			   for(int j = 0; j < pointsFound.length; j++)
			   {
				   if(pointsFound[j] != null)
				   {
					   System.out.println(pointsFound[j]);
					   logData += pointsFound[j]+"\n";
					   tmpConcat += "<p>"+pointsFound[j]+"</p>";
				   }
			   }
			   
			   pointsTxt[f] = tmpConcat;
		   }
	   }
	   
	   pageTemplate = pageTemplate.replace("<!-- [CONTENT_TITLE] -->", pageTitle);
	   
	   for(int f = 1; f <= 3; f++)
	   {
		   String contentBlock = "";
		   
		   contentBlock += "<h2>"+paragraphTitles[f-1]+"</h2>";
		   contentBlock += pointsTxt[f-1];

		   pageTemplate = pageTemplate.replace("<!-- [CONTENT_BLOCK_"+f+"] -->", contentBlock);
	   }
	   
	   String outputFilename = "infopage"+fileId+".html";

	   try {
		   BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/pavelandreyev/Documents/workspace-sts-3.9.9.RELEASE/Clarc/src/main/resources/static/infopages/"+outputFilename));
		   writer.write(pageTemplate);  
		   writer.close();
	   } catch(Exception e) {}

	   return finalScore;
	}
	
	public static String findTitle(String[] txt)
	{
		String title = "";
		int maxScore = 0;
		
		for(int f = 0; f < txt.length; f++)
		{
			int combinedScore = 0;
			String s = " "+txt[f];

			for(int i = 0; i < titleWords.length; i++)
			{
			    if(s.indexOf(titleWords[i]) >= 0) combinedScore++;
			}
			
			if(combinedScore >= maxScore)
			{
				maxScore = combinedScore;
				title = txt[f];
			}
		}

		return title;
	}
	
	public static String[] findParagraphTitle(String[] txt, int indx)
	{
		String[] paragraphTitleData = {"", ""};
		int wordDensityPercent = 20;

		for(int f = indx; f < txt.length; f++)
		{
			int combinedScore = 0;
			String s = " "+txt[f];

			for(int i = 0; i < paragraphWords.length; i++)
			{
			    if(s.indexOf(paragraphWords[i]) >= 0)
			    {
			    	combinedScore++;
			    }
			}
			
			String[] words = txt[f].split(" ");
			
			if(combinedScore*100/words.length >= wordDensityPercent)
			{
				paragraphTitleData[0] = txt[f];
				paragraphTitleData[1] = Integer.toString(f);

				break;
			}
		}

		return paragraphTitleData;
	}

	
	public static String[] findPoints(String[] txt)
	{
		String[] points = new String[100];
		int cntr = 0;

		for(int f = 0; f < txt.length; f++)
		{
			String s = " "+txt[f];
	    	
			for(int i = 0; i < pointWords.length; i++)
			{
			    if(s.indexOf(pointWords[i]) >= 0)
			    {
			    	if(!emptyLine(txt[f]))
			    	{
			    	    txt[f] = txt[f].replace(pointWords[i], "<b>"+pointWords[i]+"</b>");
			    	    points[cntr] = txt[f];
			            cntr++;
			    	}
			        break;
			    }
			}
		}

		return points;
	}
	
	public static String[] cutParagraph(String[] textData, int startIndx, int endIndx)
	{
		String[] slice = new String[endIndx - startIndx + 1];
		
		for(int i = 0; i < slice.length; i++)
		{
			if((startIndx+i) < textData.length)
			{
			    slice[i] = textData[startIndx+i];
			}
			else
			{
				slice[i] = "";
			}
		}
		
		return slice;
	}
	
	public static boolean emptyLine(String line)
	{	
		String[] validChars = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я", "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я",};
		
		for(int i = 0; i < validChars.length; i++)
		{
		    if(line.indexOf(validChars[i]) >= 0)
		    {
		    	return false;
		    }
		}
		
		return true;
	}
}
