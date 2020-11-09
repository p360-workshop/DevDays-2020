## Lab 2 - Search

1. Find the "Search" field near the top of the screen.
1. Type in one of the values noted in the last lab and hit the Enter key or the Update button.
    1. Notice the results change and the value you used to search is highlighted in the results.
1. Type in "jackson" in the search field and hit Enter.
    1. Notice the results have a lot of customers with the first name Jackson. 
    1. There are also some results where the last name is Jackson.
    1. Try other variations on Jackson (e.g. jacksons, jackso, jack, JACKSON)
1. Let's narrow this down to only those users that have a last name of Jackson.
    1. In the search field, type in `customer_last_name: "Jackson"`
    1. Make sure your date range is large enough (try last 30 days if you are not seeing results).
    1. Now try the same search with a lowercase `j` on Jackson.  `customer_last_name: "jackson"`
    1. Now try the same query, but look at the keyword mapping on last name.
        * customer_last_name.keyword: "jackson"
        * customer_last_name.keyword: "Jackson"
    1. Using "keyword" (if your property is mapped like this) does exact value searching.
    1. Find products where the `taxless_total_price` is between $60 and $65.
    

[Lab 3 - Visualize - Explore](https://github.com/raghaj/DevDays-2020/ElasticStack/labs/03-lab)