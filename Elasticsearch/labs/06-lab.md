## Lab 6 - Hands on demo - continued

1. Try the different forms of querying for data
  - `curl http://localhost:8080/load` - This may have already been done by someone else.
  - `curl http://localhost:8080/survey/100` - Return responses for survey 100
  - `curl http://localhost:8080/rating/9/10` - Return responses with ratings between 9 and 10
  - `curl http://localhost:8080/text/download` - Return responses with keyword `download`
  
1. Try:
  - Using different survey ids between 100 and 103
  - Using different rating ranges
  - Using different text searches (e.g. words like `handler, download, listen, dash, prey`).