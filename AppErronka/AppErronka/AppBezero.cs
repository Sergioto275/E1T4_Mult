using System;
using System.Diagnostics;
using System.IO;
using System.IO.Pipes;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace AppErronka
{
    public partial class AppBezero : Form
    {
        private string _bezeroIzena;
        private int _bezeroId;
        private string _ostatuMota;
        private AdminApp _adminApp;
        private Process jokoaProzesua;

        public AppBezero(string izena, int id, string ostatua, AdminApp admin)
        {
            InitializeComponent();

            _bezeroId = id;
            _bezeroIzena = izena;
            _ostatuMota = ostatua;
            _adminApp = admin;
            string bezeroDatuak = $"Izena: {izena}  ID: {id}  Ostatu mota: {ostatua}";
            label1.Text = bezeroDatuak;

            EskareaComboBox.Items.Add("Eskuoiak");
            EskareaComboBox.Items.Add("Izarak");
            EskareaComboBox.Items.Add("Zaborra");
            EskareaComboBox.Items.Add("Jatetxe");

            EskareaComboBox.SelectedIndex = 0;
        }

        private void EskaeraEginButton_Click(object sender, EventArgs e)
        {
            string eskaeraMota = EskareaComboBox.SelectedItem?.ToString();

            if (string.IsNullOrEmpty(eskaeraMota))
            {
                MessageBox.Show("Mesedez, aukeratu eskaera bat.");
                return;
            }

            _adminApp.EskaeraGehitu(eskaeraMota, _bezeroId, "Langilea 1", "Ez");

            
        }

        // Implementación simple del botón BideojokoaHasi
        private void BideojokoaHasiButton_Click(object sender, EventArgs e)
        {
            try
            {
                // Generamos la ruta relativa correctamente utilizando el directorio del proyecto
                string jokoaBidea = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "..", "JokoaBuild", "Joko_simulazioa.exe");


                // Iniciar el juego
                jokoaProzesua = Process.Start(jokoaBidea);

                // Habilitar eventos para saber cuándo el proceso ha terminado
                jokoaProzesua.EnableRaisingEvents = true;
                jokoaProzesua.Exited += JokoaProzesua_Amaitu;

                
            }
            catch (Exception ex)
            {
                // Si ocurre un error al intentar abrir el juego, mostrar el mensaje de error
                MessageBox.Show($"Errorea jokoaren hasierarekin: {ex.Message}");
            }
        }

        private void JokoaProzesua_Amaitu(object sender, EventArgs e)
        {
            // Cuando el juego termine, mostrar el mensaje
            
        }

        // Implementación simple del botón CheckOut
        private void CheckOutButton_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Ziur zaude Checkout nahi duzula?", "Checkout", MessageBoxButtons.YesNo);
            if (result == DialogResult.Yes)
            {
                this.Close(); // Cerrar la ventana
            }
        }

        public void MezuaJaso(string mezua)
        {
            // Verifica si se necesita invocar en el hilo principal de la interfaz
            if (InvokeRequired)
            {
                Invoke(new Action<string>(MezuaJaso), mezua);
            }
            else
            {
                textBox1.Text = mezua; // Actualiza el TextBox con el mensaje recibido
            }
        }

        

        public int BeZeroId
        {
            get { return _bezeroId; }
        }
    }
}
