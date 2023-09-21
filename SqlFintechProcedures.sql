CREATE DEFINER=`root`@`%` PROCEDURE `java47`.`CalcSumPackage`(IN idPackageIN 
DOUBLE, IN timePackageIN DATETIME,IN typeInt VARCHAR(10), IN lengthInt INT, 
IN dateFrom DATE, IN dateTo DATE,
OUT res TEXT)
BEGIN
WITH t3 AS (SELECT t2.vChange, t2.date_stock FROM (SELECT t1.date_stock, 
t1.closevA, t1.dateCompare, packageV.closevA AS closevEnd, 
packageV.closevA - t1.closevA AS vChange
FROM
(SELECT  date_stock, closevA, 
CASE  
WHEN (typeInt LIKE "YEAR") OR (typeInt LIKE "YEARS") THEN 
DATE_ADD(date_stock, INTERVAL lengthInt YEAR) 
WHEN  (typeInt LIKE "MONTH") OR (typeInt LIKE "MONTHS")  
THEN DATE_ADD(date_stock, INTERVAL lengthInt MONTH)
WHEN  (typeInt LIKE "WEEK") OR (typeInt LIKE "WEEKS")  THEN 
DATE_ADD(date_stock, INTERVAL lengthInt WEEK)
ELSE DATE_ADD(date_stock, INTERVAL lengthInt DAY)
END AS dateCompare 
FROM (Select date_stock, SUM(closevAmount) AS closevA FROM
(Select st.name, st.closev, st.date_stock,  na.amount, 
st.closev *  na.amount as closevAmount
From java47.stocks as st, (Select name, amount 
FROM  java47.name_amount 
     WHERE id_package  = idPackageIN ) na
WHERE  UPPER(st.name) = UPPER(na.name) and st.date_stock 
BETWEEN dateFrom AND dateTo and st.work_day_or_not = TRUE) as stAmount
GROUP By date_stock) as ttt) AS t1 
LEFT  JOIN
(Select date_stock, SUM(closevAmount) AS closevA FROM
(Select st.name, st.closev, st.date_stock,  na.amount, st.closev *  
na.amount as closevAmount
From java47.stocks as st, (Select name, amount FROM  
java47.name_amount 
     WHERE id_package  = idPackageIN ) na
WHERE  UPPER(st.name) = UPPER(na.name) and st.date_stock BETWEEN 
dateFrom AND dateTo) as stAmount
GROUP By date_stock) AS packageV ON t1.dateCompare = 
packageV.date_stock) AS t2
WHERE t2.vChange IS NOT NULL  AND t2.dateCompare <= dateTo)
Select  CONCAT(maxV, ', ', mean, ', ', median, ', ', minV, ', ', std) INTO 
res FROM
(Select MAX(vChange) as maxV, AVG(vChange) as mean,  MIN(vChange) as minV, 
STDDEV_SAMP(vChange) AS std FROM t3) AS t6,
(SELECT SUM(vChange)/COUNT(*) as median FROM 
(SELECT vChange, row_num, total_rows/2 FROM 
(SELECT vChange, ROW_NUMBER() OVER (ORDER BY vChange) AS row_num, COUNT(*) 
OVER () AS total_rows FROM t3) AS t4
where  row_num >= (total_rows+1)/2 - 0.6 AND row_num <= (total_rows+1)/2 + 
0.6) AS t5) AS t7;
END
CREATE DEFINER=`root`@`%` PROCEDURE `java47`.`CorrelationCalc`(IN nameIndex1 
VARCHAR(50), IN nameIndex2 VARCHAR(50), IN dateFrom DATE, IN dateTo DATE, 
OUT res DOUBLE)
BEGIN
With t1 AS 
(Select closev as close1, name as name1, date_stock From java47.stocks 
  where name LIKE nameIndex1 and date_stock BETWEEN dateFrom AND 
dateTo and work_day_or_not = TRUE)
Select  (COUNT(*) * SUM(x * y) - SUM(x) * SUM(y)) / 
    (SQRT((COUNT(*) * SUM(x * x) - SUM(x) * SUM(x)) * (COUNT(*) * SUM(y * y) 
- SUM(y) * SUM(y)))) INTO res 
from
(Select java47.stocks.closev as x, close1 as y  From java47.stocks, t1
where java47.stocks.name LIKE nameIndex2 and 
java47.stocks.date_stock = t1.date_stock 
and (java47.stocks.closev > 0) AND (close1 > 0)) AS t2;
END
CREATE DEFINER=`root`@`%` PROCEDURE `java47`.`IncomeWithAPY`(IN nameIndex 
VARCHAR(50), IN typeInt VARCHAR(10), IN lengthInt INT, IN lengthYears DOUBLE,
IN dateFrom DATE, IN dateTo DATE)
BEGIN
WITH t3 AS 
(SELECT date_stock, closev, dateCompare, closevEnd, vChange  FROM (SELECT 
t1.name, t1.date_stock, t1.closev, t1.dateCompare, java47.stocks.closev AS 
closevEnd, 
java47.stocks.closev - t1.closev AS vChange
FROM
(SELECT name, date_stock, closev, DATE_ADD(date_stock, INTERVAL lengthInt 
YEAR) AS dateCompare 
FROM java47.stocks WHERE name LIKE nameIndex AND work_day_or_not = TRUE AND 
date_stock BETWEEN dateFrom AND dateTo) AS t1 
LEFT  JOIN java47.stocks ON t1.name = java47.stocks.name AND t1.dateCompare =
java47.stocks.date_stock) AS t2
WHERE t2.vChange IS NOT NULL AND t2.dateCompare <= dateTo)
Select date_stock AS date_of_purchase, closev AS purchase_amount, dateCompare
AS date_of_sale,  
closevEnd AS sale_amount, vChange AS income, 
(POWER(closevEnd/closev, 1/lengthYears) - 1) *100 AS dimension
FROM
(Select * FROM (Select * from t3 where vChange = (SELECT MIN(vChange) from 
t3) LIMIT 1) AS t4
UNION ALL
Select * FROM (Select * from t3 where vChange = (SELECT MAX(vChange) from t3)
LIMIT 1) AS t5)  AS t6;
END
CREATE DEFINER=`root`@`%` PROCEDURE `java47`.`PeriodForIndexInInterval`(IN 
nameIndex VARCHAR(50), IN typeInt VARCHAR(10), IN lengthInt INT, IN dateFrom 
DATE, IN dateTo DATE, 
OUT res TEXT)
BEGIN
WITH t3 AS 
(SELECT t2.vChange, t2.date_stock FROM (SELECT t1.name, t1.date_stock, 
t1.closev, t1.dateCompare, java47.stocks.closev AS closevEnd, 
java47.stocks.closev - t1.closev AS vChange
FROM
(SELECT name, date_stock, closev, 
CASE  
WHEN (typeInt LIKE "YEAR") OR (typeInt LIKE "YEARS") THEN 
DATE_ADD(date_stock, INTERVAL lengthInt YEAR) 
WHEN  (typeInt LIKE "MONTH") OR (typeInt LIKE "MONTHS")  
THEN DATE_ADD(date_stock, INTERVAL lengthInt MONTH)
WHEN  (typeInt LIKE "WEEK") OR (typeInt LIKE "WEEKS")  THEN 
DATE_ADD(date_stock, INTERVAL lengthInt WEEK)
ELSE DATE_ADD(date_stock, INTERVAL lengthInt DAY)
END AS dateCompare 
FROM java47.stocks WHERE name LIKE TRIM(nameIndex) AND work_day_or_not = TRUE
AND date_stock BETWEEN dateFrom AND dateTo) AS t1 
LEFT  JOIN java47.stocks ON t1.name = java47.stocks.name AND t1.dateCompare =
java47.stocks.date_stock) AS t2
WHERE t2.vChange IS NOT NULL  AND t2.dateCompare <= dateTo)
Select  CONCAT(maxV, ', ', mean, ', ', median, ', ', minV, ', ', std) INTO 
res FROM
(Select MAX(vChange) as maxV, AVG(vChange) as mean,  MIN(vChange) as minV, 
STDDEV_SAMP(vChange) AS std FROM t3) AS t6,
(SELECT SUM(vChange)/COUNT(*) as median FROM 
(SELECT vChange, row_num, total_rows/2 FROM 
(SELECT vChange, ROW_NUMBER() OVER (ORDER BY vChange) AS row_num, COUNT(*) 
OVER () AS total_rows FROM t3) AS t4
where  row_num >= (total_rows+1)/2 - 0.6 AND row_num <= (total_rows+1)/2 + 
0.6) AS t5) AS t7;
END