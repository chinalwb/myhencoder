### Git 交互式 rebase

* git checkout d11d71 // head 指向 d11d71
* git checkout master // head 指向 master
* git checkout --detach // head 指向 d11d71

* git merge feature1 // 将 feature1 分支内容合并到当前分支(比如master)
* git branch -d feature1 // 删除feature1分支

* git branch feature3
* 做修改，然后 add / commit
* git push origin feature3 // 把feature3 分支直接推到远端

* git merge feature3 // 合并分支

* git merge feature3 --no--ff // 不使用fast-forward方式合并，保留分支的commit历史


* git log --graph // 以图形展现log

#### rebase

* git checkout feature2
* git rebase master // 把feature2 rebase到master
* rebase的实质是将feature当中的commits 复制一份并在rebase的目标上执行这些提交

* git rebase 也可能会发生冲突
* 发生冲突之后，手动解决, 然后再执行:
* git add .
* git rebase --continue

* git rebase -i master // 显示并选择从当前commit到master之间的commits，然后rebase
* git rebase -i HEAD~3 // 显示从HEAD开始前面3条commits
* git commit --amend // 


* git revert b188912 // 删除某个已经push过的commit


#### reset

* git reset --hard feature3 // master 指向了feature3的最后一个commit， 暂存区是没有东西的

* git reset feature3 // master 指向了feature3的最后一个commit，暂存区是有文件改动的
