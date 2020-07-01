object Huffman {
    fun CodeH(A: ByteArray, F: IntArray): Arbre? {
        val liste = LCT()
        println("Construction des feuilles... et insertion dans la liste... ")
        for (i in A.indices) liste.Ajouter(Feuille(A[i]), F[i])
        println("Construction du code... ")
        var p1: PaireFA
        var p2: PaireFA
        var taille = 0;
        while (liste.taille > 1) {
            taille++;
            p1 = liste.ExtraireMin()
            p2 = liste.ExtraireMin()
            liste.Ajouter(Noeud(p1.a, p2.a), p1.freq + p2.freq)
            if(taille ===150000)
              println(150000)
            if(taille ===200000)
                println(taille)
        }
        p1 = liste.ExtraireMin()
        // on finalise le code et faisant pointer les feuilles à la racine...
        val code = p1.a
        code!!.CompleterRacine(code)
        return code
    }

    fun Decoder(m: String?, code: Arbre?): String {
        return code!!.Decoder(m, 0)
    }

 /*   @JvmStatic
    fun main(args: Array<String>) {
        //int[] F = {45,13,12,16,9,5};
        //	char[] A = {'a', 'b', 'c', 'd', 'e', 'f'};
        val F = intArrayOf(4, 13, 12, 16, 9, 5, 8, 19, 20, 11, 6, 9)
        val A = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l')
        val Code = CodeH(A, F)
        Code!!.affiche()
        println("Decodage...")
        println("001011100110111110000")
        println(Decoder("001011100110111110000", Code))
    }*/

    // Les arbres binaires pour representer les codes...
    abstract class Arbre {
        fun affiche() {
            afficheCode("")
        }

        abstract fun afficheCode(s: String?)
        abstract fun CompleterRacine(r: Arbre?)
        abstract fun Decoder(m: String?, i: Int): String
    }

    internal class Noeud(var fg: Arbre?, var fd: Arbre?) : Arbre() {
        override fun afficheCode(s: String?) {
            fg!!.afficheCode(s + "0")
            fd!!.afficheCode(s + "1")
        }

        override fun CompleterRacine(r: Arbre?) {
            fg!!.CompleterRacine(r)
            fd!!.CompleterRacine(r)
        }

        override fun Decoder(m: String?, i: Int): String {
            if(m != null) {
                if (i >= m.length) return ""
                return if (m[i] == '0') fg!!.Decoder(m, i + 1) else fd!!.Decoder(m, i + 1)
            }
            return ""
        }

    }

    internal class Feuille(var lettre: Byte) : Arbre() {
        var racine: Arbre? = null
        override fun afficheCode(s: String?) {
            println("$lettre -> $s")
        }

        override fun CompleterRacine(r: Arbre?) {
            racine = r
        }

        override fun Decoder(m: String?, i: Int): String {
            return lettre.toString() + racine!!.Decoder(m, i)
        }

    }

    // Paire arbre,freq
    internal class PaireFA(var freq: Int, var a: Arbre?)

    // Liste chainee triee selon freq
    internal class LCT {
        internal class Cell(f: Int, arb: Arbre?, s: Cell?) {
            var paire: PaireFA
            lateinit var suiv: Cell

            init {
                paire = PaireFA(f, arb)
                if (s != null) {
                    suiv = s
                } else {
                    println("c'est le bordel")
                }
            }
        }

        var premier: Cell? = null
        var taille = 0
        fun Ajouter(arb: Arbre?, f: Int) {
            taille++
            if (premier == null) {
                premier = Cell(f, arb, null)
            } else if (premier!!.paire.freq >= f) {
                premier = Cell(f, arb, premier!!)
                return
            } else {
                var prec: Cell? = null
                var aux: Cell = premier as Cell
                while (aux != null && aux.paire.freq < f) {
                    prec = aux
                    aux = aux.suiv
                }
                prec!!.suiv = Cell(f, arb, aux)
            }
        }

        fun ExtraireMin(): PaireFA {
            if (premier == null) {
                println("ExtraireMin : erreur ! Liste vide !")
                return PaireFA(0, null)
            }
            taille--
            val p = premier!!.paire
            premier = premier!!.suiv
            return p
        }

    } // de la classe LCT
}