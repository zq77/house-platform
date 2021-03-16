- login
```
    1. cd inventories/prod/
    2. 将 证书 放到 指定位置 “~/.ssh/house-deploy.key” 
    3. chmod 400 ~/.ssh/house-deploy.key
    4. ssh -F ssh-config 212.64.23.17 //登陆系统
```

- deploy
```
    1. install python, pip
    2. pip install virtualenv
    3. $ cd deploy
    4. $ virtualenv -p $(which python) --always-copy venv
    5. $ source venv/bin/activate
    6. $ pip install -r requirements.txt -i https://pypi.douban.com/simple
    7. $ ansible-playbook -i inventories/prod/host.ini -e "mysql_password=QgOHMgm?Y5#p" house-platform.yml
    8. $ ansible-playbook -i inventories/prod/host.ini -e "mysql_password=QgOHMgm?Y5#p init_deploy=true" house-platform.yml
```