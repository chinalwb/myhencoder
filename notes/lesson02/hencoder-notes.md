### 编码、加密、Hash

对称加密

对称加密的经典算法：
	DES 56位密钥
	AES 密钥比较长 128位

密钥被试完了 怎么理解？密钥总数是一定的吗？


非对称加密
	加解密用的算法是同一个算法

	加密密钥和解密密钥是特定算法生成的

	0123456789

	110 -- 要加密的数据

	加密密钥 为 4， 解密密钥为 6. -- 生成一对密钥对

	1+4 = 5
	1+4 = 5
	0+4 = 4

	554 -- 加密结果

	5+6 = 11 -- 1
	5+6 = 11 -- 1
	4+6 = 10 -- 0

	110 -- 解密结果

Base58
	去掉了
		I / l
		O / 0
		+ 和 /
	用在了比特币
	比特币可能会手抄
	I / l 和 O / 0 容易混淆
	+ 和 / 去掉是为了双击选中

URL encoding

& = %26
+ : 用在搜索引擎
/ : 地址栏路径



为什么一个5G的电影可以算出来几十个字符长度的hash呢？
-- 是根据一个文件的元信息来计算hash的 而不是仅仅根据文件的内容

Hash算法例如SHA-1
会对文件的所有内容进行消息摘要
得到一个固定长度 - 160bit， 20byte的数字摘要，这个摘要通常显示成长度为40的字符串。