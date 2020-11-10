## Lab 4 - Visualize - Create
1. Click on the Visualize link in the left menu.
1. Click the blue "Create Visualization" button.
1. Choose Pie chart
1. Choose "kibana_sample_data_ecommerce" (this is the index we want the data to come from)
1. On right column click "Add" under Buckets, then "Split slices".
1. Under Aggregation select "Terms"
1. Under Field choose "day_of_week"
1. Set size to 7.
1. Be sure time span is large enough if you are not seeing data (i.e. last 15 days)
1. Click update button on bottom right.  What is the pie chart showing?
    1. Add a second slice to find top 2 cities with orders for each day.
    1. Are they the same cities each day?
    
[Lab 5 - Visualize - Create](https://github.com/p360-workshop/DevDays-2020/blob/feature/es/Elasticsearch/labs/05-lab.md)