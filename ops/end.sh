echo "\n\n========== end routine start! ==========\n\n"

echo "\n\n========== step 1. backend down! ==========\n\n"
docker-compose.backend.yaml down -d
echo "\n\n========== step 1. finish ==========\n\n"

echo "\n\n========== step 2. databases down! ==========\n\n"
docker-compose.database.yaml down -d
docker ps
echo "\n\n========== step 2. finish ==========\n\n"
sleep 2

echo "\n\n========== step 3. check! ==========\n\n"
docker ps
echo "\n\n========== step 3. finish ==========\n\n"

echo "\n\n========== end routine finish! ==========\n\n"