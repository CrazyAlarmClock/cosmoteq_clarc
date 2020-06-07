from requests import *
import xlrd, xlwt, openpyxl
from xlutils.copy import copy
import datetime
from time import *
import sys
from random import *

person_number    = 0
global_person_DB = []

start_page_number = 1
end_page_number   = 307

profiles = set()

wb = xlwt.Workbook()
ws1 = wb.add_sheet('Users')
ws2 = wb.add_sheet('Blocks')

ws1.write(0, 0, "Write_Number")
ws1.write(0, 1, "User_Id")
ws1.write(0, 2, "FirstName");
ws1.write(0, 3, "MiddleName");
ws1.write(0, 4, "LastName")
ws1.write(0, 5, "Email");
ws1.write(0, 6, "Role");
ws1.write(0, 7, "Reading_Speed"); # скорость просмотра страницы
ws1.write(0, 8, "Page_End_Read"); # досматриваемость страницы
ws1.write(0, 9, "Registration_Date") # Дата регистрации
ws1.write(0, 10, "Last_Login")
ws1.write(0, 11, "Buy_Status")
ws1.write(0, 12, "Buy_Price")

ws2.write(0, 0, "Write_Number")
ws2.write(0, 1, "Block_Id")
ws2.write(0, 2, "Time_In")
ws2.write(0, 3, "Time_Out")
ws2.write(0, 4, "User_Id")
ws2.write(0, 5, "Block_Number") # Порядковый номер блока на странице
ws2.write(0, 6, "Page_Url")

All_Roles = ["C++ Developer", "UIX Designer", "Economist", "English Teacher"]

def random_buy_status():
        Buy_Status = randint(0, 1)
        return Buy_Status

def random_write(string_number, wsi, wb, table_number, parametr):
        global All_Roles
        if parametr == 1:
                for i in range(1, string_number):
                        for j in range(table_number):
                                Buy_Status = random_buy_status()
                                if j == 0: 
                                        wsi.write(i, j, str(i))
                                elif j == 6:
                                        p = randint(0, len(All_Roles) - 1)
                                        wsi.write(i, j, All_Roles[p])
                                elif j == 11: 
                                        if random_buy_status() == 1:
                                                wsi.write(i, j, 1)
                                                wsi.write(i, 12, (str(randint(1, 1000000)) + "." + str(randint(1, 1000000))))
                                        else:
                                                wsi.write(i, j, 0)
                                                wsi.write(i, 12, 0)
                                elif j == 4:
                                        wsi.write(i, j, str(randint(1, 10)))
                                elif j != 12:
                                        wsi.write(i, j, str(randint(1, 10)))
        else:
                for i in range(1, string_number):
                        for j in range(table_number):
                                Buy_Status = random_buy_status()
                                if j == 0: 
                                        wsi.write(i, j, str(i))
                                elif j == 11: 
                                        if random_buy_status() == 1:
                                                wsi.write(i, j, 1)
                                                wsi.write(i, 12, (str(randint(1, 1000000)) + "." + str(randint(1, 1000000))))
                                        else:
                                                wsi.write(i, j, 0)
                                                wsi.write(i, 12, 0)
                                elif j == 2 or j == 3: wsi.write(i, j, str(randint(100, 10000)))
                                elif j == 4:
                                        wsi.write(i, j, str(randint(1, 10)))
                                elif j != 12:
                                        wsi.write(i, j, str(randint(1, 10)))               
        return wsi

def rewrite_date():
        global ws1, ws2

        ws1 = random_write(100, ws1, wb, 13, 1)
        ws2 = random_write(100, ws2, wb, 7, 2)
        wb.save('/Users/andreevalexander/Desktop/Teqstack/DB.xls')

def clean(parametr, text):
        ans = []
        if parametr == 0:
                for i in range(len(text)):
                        ans.append((text[i]).replace("'", ""))
        return ans


def find_out(temp_Personal_Id, vals_blocks):
        '''
        ws2.write(0, 0, "Write_Number") # Номер записи
        ws2.write(0, 1, "Block_Id") # Индекс блока
        ws2.write(0, 2, "Time_In") # Время входа в блок
        ws2.write(0, 3, "Time_Out") # Времы выхода из блока
        ws2.write(0, 4, "User_Id") # Id пользователя
        ws2.write(0, 5, "Block_Number") # Порядковый номер блока на странице
        ws2.write(0, 6, "Page_Url") # Индекс страницы

        result = [Среднее время, проведенное в блоке, ..., ...]
        '''
        result = [-1, 1, 1]
        for i in range(1, len(vals_blocks)):
                if vals_blocks[i][4] == temp_Personal_Id:
                        if result[0] == -1:
                                result[0] = abs(int(vals_blocks[i][3]) - int(vals_blocks[i][2]))
                        elif result[0] != -1:
                                result[0] = 0.5 * abs((result[0] + int(vals_blocks[i][3]) - int(vals_blocks[i][2])))
        
        return result

def middle(temp_statics, temp_Personal_Statistics):
      result = []
      for i in range(0, len(temp_statics)):
            result.append(0.5 * (float(temp_Personal_Statistics[i]) + float(temp_statics[i])))
      return result

def open_docs():
	DB = { "1":"Среднее время проведенное в блоке в сек.: ", "2":"Параметр 2: ", "3":"Параметр 3: "}
	source_filename = "/Users/andreevalexander/Desktop/Teqstack/DB.xls"

	rb = xlrd.open_workbook(source_filename, on_demand=True)  # Открываем исходный документ
        #выбираем активный лист
	users_sheet = rb.sheet_by_index(0)
	blocks_sheet = rb.sheet_by_index(1)
        
        #получаем список значений из всех записей
	vals_users = [users_sheet.row_values(rownum) for rownum in range(users_sheet.nrows)]
	vals_blocks = [blocks_sheet.row_values(rownum) for rownum in range(blocks_sheet.nrows)]
	answer = open("output.txt", "w")

	answer.write("Teqstack. Пример работы аналитики на Python.\n\n")

	return (answer, vals_users, vals_blocks, rb, users_sheet, blocks_sheet, DB)

def transform(newdict):
        newlist = list()
        for i in newdict.keys():
                newlist.append(i)
        return newlist
      #return clean(0, str(Users_id_Roles.keys())[11:len(str(Users_id_Roles.keys())) - 2].split(","))

def analize_date():

      answer, vals_users, vals_blocks, rb, users_sheet, blocks_sheet, DB = open_docs()
      
      '''
	    Users_id_Roles = {role:['1', '2', ...]}
	    Roles_Statistics {role:[]}
      '''
      Users_id_Roles = dict()
      Roles_Statistics = dict()
      for i in range(1, len(vals_users)):
            try:
                  information = Users_id_Roles[vals_users[i][6]]
                  information.append(vals_users[i][1])
                  Users_id_Roles[vals_users[i][6]] = information
            except:
                  Users_id_Roles[vals_users[i][6]] = [vals_users[i][1]]
      all_roles = transform(Users_id_Roles)
      
      result = ""
      for i in range(len(all_roles)):
            temp_role = all_roles[i]
            temp_Users_Id = Users_id_Roles[all_roles[i]]
            temp_statics = []
            for j in range(len(temp_Users_Id)):
                  temp_Personal_Id = temp_Users_Id[j]
                  temp_Personal_Statistics = find_out(temp_Personal_Id, vals_blocks)
                  if len(temp_statics) == 0:
                        temp_statics = temp_Personal_Statistics
                  else:
                        temp_statics = middle(temp_statics, temp_Personal_Statistics)
                        
            result += "Специализация: " + str(temp_role) + "\n"
            for j in range(len(temp_statics)):
                    result += str(DB[str(j + 1)]) + str(temp_statics[j]) + " сек.\n";
            result += "\n"    
            
      print(result)
      answer.write(str(result))

def main():
      temp = int(input("What to do? 0 or not 0 -> "))
      if temp == 0:
            rewrite_date()
      else:
            analize_date()

main()
