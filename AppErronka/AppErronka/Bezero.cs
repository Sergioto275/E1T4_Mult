using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppErronka
{
    public class Bezero
    {
        public string Izena { get; set; }
        public int Id { get; set; }

        public Bezero(string izena, int id)
        {
            Izena = izena;
            Id = id;
        }

        public override string ToString()
        {
            return $"{Id}, {Izena}";
        }
    }
}
