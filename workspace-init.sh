#!/bin/bash
# This script creates nested git repository in dms-workspace folder in the directory where it is ran
# Run as Administrator
echo "======================================="
echo "Cloning DocumentManagementSystem-FrontEnd into DocumentManagementSystem-Backend/src/main/resources/static"
cd src/main/resources
rm -rf static
git clone https://github.com/Igor-Jovic/DocumentManagementSystem-Frontend.git static

echo "======================================="
echo "Downloading frontend dependencies"
cd static
sudo bower install --allow-root
