echo "\n\n========== step 1. backend image build start! ==========\n\n"
#docker build . -t backend:1.0
echo "\n\n========== step 1. finish ==========\n\n"
sleep 2

echo "\n\n========== step 2. run databases! ==========\n\n"
docker-compose up -d
docker ps
echo "\n\n========== step 2. finish ==========\n\n"
sleep 2

echo "\n\n========== step 3. alias setting! ==========\n\n"
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
echo "\n\n========== step 3. finish ==========\n\n"
sleep 2

echo "\n\n========== step 4. backend run! ==========\n\n"
docker run backend:1.0 --name="backend" -p 8000:8000 --link mariadb:mariadb elasticsearch:elasticsearch redis:redis
echo "\n\n========== step 4. finish ==========\n\n"