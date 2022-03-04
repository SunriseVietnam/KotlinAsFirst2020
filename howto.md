git remote add upstream-my https://github.com/SunriseVietnam/KotlinAsFirst2021
git fetch upstream-my
git checkout -b backport
git cherry-pick d535f3592006b8f2593c9f881d72c05164aadc13...FETCH_HEAD
git remote add upstream-theirs https://github.com/redhair-demon/KotlinAsFirst2021
git fetch upstream-theirs
git checkout master
git merge backport upstream-theirs/master
...
git remote -v