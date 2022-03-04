# Ход выполнения практики
1. `git remote add upstream-my https://github.com/SunriseVietnam/KotlinAsFirst2021`
2. `git fetch upstream-my`
3. `git checkout -b backport`
4. `git cherry-pick d535f3592006b8f2593c9f881d72c05164aadc13...FETCH_HEAD`
5. `git remote add upstream-theirs https://github.com/redhair-demon/KotlinAsFirst2021`
6. `git fetch upstream-theirs`
7. `git checkout master`
8. `git merge backport upstream-theirs/master`
9. `...`
10. `git remote -v`
