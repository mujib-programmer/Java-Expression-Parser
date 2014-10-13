Java-Expression-Parser
======================

A Recursive-Descent Expression Parser
oleh Herb Schildt

Karena terdapat banyak expression, penting untuk diketahui apa yang merupakan expression. Program ini hanya cocok untuk satu macam expression, yaitu ekspresi numerik. Ekspresi numerik terdiri atas : 
* Angka 
* Operator +, -, /, *, ^, %, = 
* tanda kurung 
* variabel 

tanda ^ berarti perpangkatan, bukan operasi XOR seperti di Java, dan = adalah operator penugasan. contoh expresi :

* 10-8 
* (100-5) * 14/6 
* a+b-c 
* 10^5 
* a=10-b 

### Berikut ini adalah urutan proses yang didahulukan berdasarkan operatornya
1. +-(unary) 
2. ^ 
3. */% 
4. +- 
5. = 

Nomor 1 berarti prioritas didahulukan adalah yang paling besar, sedangkan nomor 5 adalah prioritas yang paling akhir.

Semua variabel tidak case sensitive, jadi variabel a akan sama dengan variabel A. Dan aturan lainnya, semua nilai angka diasumsikan bertipe double meskipun kamu dengan mudah bisa memodifikasi parser untuk menangani tipe data yang lain.

Parser akan dibuat sedemikian rupa sehingga logika didalamnya jelas dan mudah difahami, hanya pengecekan kesalahan dasar yang dimasukkan.

dari buku Art Of Java

### Contoh penggunaan pada applet kalkulator

![Alt text](expression calculator.png?raw=true "Applet Kalkulator Ekspresi")

Tips:  
* compile semua file java
* buka applet dengan membuka file Calc.html dengan browser
* atau buka applet dengan applet viewer dengan mengetikkan appletviewer Calc.html
* isikan ekspresi matematika yang ingin dihitung hasilnya
* tekan enter setelah menuliskan ekspresi yang ingin dihitung.
* selamat mencoba 
