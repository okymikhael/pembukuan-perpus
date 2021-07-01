package com.example.perpustakaan

class data_buku {
    var nama: String? = null
    var namaBuku: String? = null
    var namaPenulis: String? = null
    var penerbit: String? = null
    var tahunTerbit: String? = null
    var key: String? = null

    constructor()

    constructor(namaBuku: String?, namaPenulis: String?, penerbit: String?, tahunTerbit: String?) {
        this.namaBuku = namaBuku
        this.namaPenulis = namaPenulis
        this.penerbit = penerbit
        this.tahunTerbit = tahunTerbit
    }
}