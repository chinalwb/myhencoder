### 如何计算文件的SHA-1值


文件的SHA-1并不是根据文件的名称、创建时间、所有人等这种文件的属性信息来计算出来的。

而是对文件的所有内容进行消息摘要 得到的一个固定长度（特定算法对应特定长度）的hash值。

另外 关于SHA-1:
SHA-1算法会计算出一个：160 bit, 20byte 的hash值, 通常显示为一个长度为 40 的字符串。
如：
b7cb153caa12170188aaaab4f02b3fb84afc8fbb

In cryptography, SHA-1 (Secure Hash Algorithm 1) is a cryptographic hash function which takes an input and produces a 160-bit (20-byte) hash value known as a message digest – typically rendered as a hexadecimal number, 40 digits long.


而MD5算法会计算出一个 128bit, 16byte的hash值，通常显示为一个长度为 32 的字符串。
如：
09bb0357cba463361c07659ca4305046

注：上述两个字符串是对同一个文件分别进行SHA-1和MD5算法计算出来的hash值。
-------

昨晚直播的时候 我问了一个关于hash的问题  就是对于大文件如何计算hash。

当时有位大佬说是用文件的名称 创建时间 等属性信息计算一个hash数字出来。

刚才我查了一下 得到的信息跟这个有差异。 
总体来说还是要对文件的所有内容进行消息摘要，得到一个固定长度的hash值。代码分享一下




---

MD5计算出来的也是跟操作系统命令计算出来的一样。


