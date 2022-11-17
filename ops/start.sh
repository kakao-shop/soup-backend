echo "\n\n========== start routine start! ==========\n\n"


echo "\n\n========== step 1. backend image build or pull start! ==========\n\n"
docker login
docker pull 2214yj/soup-backend:1.0
echo "\n\n========== step 1. finish ==========\n\n"


echo "\n\n========== step 2. run databases! ==========\n\n"
docker-compose -f docker-compose.database.yaml up -d
docker ps
echo "\n\n========== step 2. finish ==========\n\n"


echo "\n\n========== step 3. product data setting! ==========\n\n"
# npm install elasticdump -g
elasticdump --input=./bank1113.json --output=http://localhost:9200
echo "\n\n========== step 3. finish ==========\n\n"


echo "\n\n========== step 4. alias setting! ==========\n\n"
curl -X POST "http://localhost:9200/_aliases" -H 'Content-Type: application/json' -d '{
    "actions": [
        {
            "add" : {
                "index" : "product-2022-11-13-11-30",
                "alias" : "product"
            }
        }
    ]
}'
echo "\n\n========== step 4. finish ==========\n\n"


echo "\n\n========== step 5. backend run! ==========\n\n"
docker-compose -f docker-compose.backend.yaml up -d
docker ps
echo "\n\n========== step 5. finish ==========\n\n"

echo "\n\n========== step 6. frontend run! ==========\n\n"
docker run -p 3000:3000 --network ops_backend-db aramu/soup-frontend
echo "\n\n========== step 6. finish ==========\n\n"


echo "\n\n========== start routine finish! ==========\n\n"
