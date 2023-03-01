# InternetEngineeringProject


## commands

1. 
addUser {"username": "user1", "password": "1234", "email": "user@gmail.com", "birthDate": "1977-09-15", "address": "address1", "credit": 1500}
2. 
addProvider {"id": 1, "name": "provider1", "registryDate": "2023-09-15"}
3.
addCommodity {"id": 1, "name": "Headphone", "providerId": 1, "price": 35000, "categories": ["Technology", "Phone"], "rating": 0, "inStock": 50}
addCommodity {"id": 2, "name": "Shirt", "providerId": 1, "price": 45000, "categories": ["Cloth", "Fashion"], "rating": 0, "inStock": 100}
4. 
getCommoditiesList 
5. 
rateCommodity {"username": "user1", "commodityId": 1, "score": 7}
6.
addToBuyList {"username": "user1", "commodityId": 1}
7. 
removeFromBuyList {"username": "user1", "commodityId": 1}
8.
getCommodityById {"id":1}
9. 
getCommoditiesByCategory {"category": "Phone"}
10. 
getBuyList {"username": "user1"}