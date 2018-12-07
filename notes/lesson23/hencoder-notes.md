### Git - 1

* git clone https://github.com/chinalwb/git-demo


* 每次提交可能会拆分成多份提交
* 提交时可以不联网

* git log -- 查看提交历史


* 添加和提交是两个步骤，都做了才算提交成功
* git add README.md
* git commit

进入 输入提交信息 的页面

"增加给自己看"

保存之后提交成功。此时是提交到了本地。

* git push origin master
origin 是远端仓库，可以自定义
master 分支名称
意思是：把本地的内容提交到 远端仓库的master分支

* git clone https://github.com/chinalwb/git-demo git-demo-2 // checkout 到git-demo-2的文件夹


* git pull origin master // 从远端更新数据

----

* commit 634345asdlkfjwe23874234...989 (HEAD -> MASTER)

634345asdlkfjwe23874234...989 // 改动内容的哈希值

* git 的本地仓库是在 .git 文件夹中

* git push origin master 把本地的提交推到远端
* git pull origin master 把远端的提交拉到本地

* git branch feature1 创建新分支
* 切到某个分支就是让head指向分支
* git checkout feature1 切换到feature1的分支

* 合并

```
git checkout master -- 切换到master
git merge feature1  -- 把feature1分支合并到master
// 冲突处理
git add .
git merge --continue
```

* git status -- 查看本地仓库的状态

* master 就是远端仓库的 HEAD 所指向的分支

* origin/master -- 远端的master指向的commit
* origin/HEAD -- 远端的master的head指向的commit

-----


老师，请问下 git clone 的时候 存到我电脑上的那些文件是直接copy的远端仓库的文件，还是把所有的commit都计算一遍来得到最终的内容呢？
