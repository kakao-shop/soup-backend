echo "\n\n========== start routine start! ==========\n\n"


# 1. docker hub로부터 필요한 이미지들을 다운받기 위해 docker login 수행
echo "\n\n========== step 1. docker login ==========\n\n"
docker login
echo "\n\n========== step 1. finish ==========\n\n"


# 2. docker-compose 명령을 통해 mysql, redis, elasticsearch, kibana 컨테이너를 실행
echo "\n\n========== step 2. run databases! ==========\n\n"
docker-compose -f docker-compose.database.yaml up -d
docker ps
echo "\n\n========== step 2. finish ==========\n\n"


# 3. 어플리케이션을 실행하기 위해 필요한 상품 데이터를 세팅
# elasticdump를 이용해서 json 파일에 저장된 데이터를 elasticsearch에 import 한다.
echo "\n\n========== step 3. product data setting! ==========\n\n"
npm install elasticdump -g
elasticdump --input=./bank1113.json --output=http://localhost:9200
echo "\n\n========== step 3. finish ==========\n\n"


# 4. spring boot에서 alias를 통해 인덱스에 접근하므로 위에서 import한 상품 정보 index에 alias를 설정한다.
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


# 5. docker-compose 명령을 통해 backend, frontend 컨테이너를 실행한다.
echo "\n\n========== step 5. frontend, backend run! ==========\n\n"
docker-compose -f docker-compose.app.yaml up -d
docker ps
echo "\n\n========== step 5. finish ==========\n\n"


echo "\n\n========== start routine finish! ==========\n\n"