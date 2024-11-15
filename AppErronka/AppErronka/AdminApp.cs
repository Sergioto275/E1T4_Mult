using System;
using System.Diagnostics;
using System.IO.Pipes;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.DataFormats;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace AppErronka
{
    public partial class AdminApp : Form
    {
        // Langileentzako task-ak eta cancelatzeko token-a
        private Task langile1; // Langile 1entzako zeregina gordetzeko task bat
        private Task langile2; // Langile 2entzako zeregina gordetzeko task bat
        private CancellationTokenSource _cancellationTokenSource; // Etenaren tokena gordetzeko
        public List<Bezero> listaBezeroak;
        private string mezuaBezeroarentzat;
        private string bezeroIzena;
        private static int azkenIda = new Random().Next(1, 1000);
        private int bezeroId;

        public AdminApp()
        {
            InitializeComponent();

            listaBezeroak = new List<Bezero>();

            // Ostatu mota aukerak ComboBox-en gehitzen ditugu
            ostatuMotaComboBox.Items.Add("Denda");
            ostatuMotaComboBox.Items.Add("Autokarabana");
            ostatuMotaComboBox.Items.Add("Bungalow");

        }

        private void BezeroBerriaButton_Click(object sender, EventArgs e)
        {
            bezeroIzena = textBox1.Text;
            string ostatuMota = ostatuMotaComboBox.SelectedItem?.ToString();

            // Verificamos si los campos no están vacíos
            if (string.IsNullOrEmpty(bezeroIzena) || string.IsNullOrEmpty(ostatuMota))
            {
                MessageBox.Show("Mesedez, idatzi bezeroaren izena eta bere ostatu mota.");
                return;
            }

            // Creamos una nueva instancia de AdminApp
            AdminApp adminAppForm = (AdminApp)Application.OpenForms["AdminApp"];
            if (adminAppForm == null)
            {
                MessageBox.Show("Ez dago AdminApp irekita.");
                return;
            }

            // Generamos el nuevo ID para el Bezero
            bezeroId = IdBerriaGeneratu();  // Generamos un nuevo ID

            //Bezeroa sortu eta listan gorde
            Bezero bezeroa = new Bezero(textBox1.Text, bezeroId);
            listaBezeroak.Add(bezeroa);
            comboBox1.Items.Add(bezeroa.Izena + " " + bezeroa.Id);
            textBox1.Clear();
            comboBox1.SelectedIndex = -1; // Limpia el ComboBox


            // Mostramos la información en la interfaz de usuario
            AppBezero appBezeroForm = new AppBezero(bezeroIzena, bezeroId, ostatuMota, this);
            appBezeroForm.Show();  // Mostramos la ventana de AppBezero

            
        }

        private async void LanaldiaHasiButton_Click(object sender, EventArgs e)
        {
            if (_cancellationTokenSource != null)
            {
                _cancellationTokenSource.Cancel();
                _cancellationTokenSource.Dispose();

            }

            _cancellationTokenSource = new CancellationTokenSource();

            try
            {
                langile1 = Task.Run(() => LangileLana("Langile 1", _cancellationTokenSource.Token));
                langile2 = Task.Run(() => LangileLana("Langile 2", _cancellationTokenSource.Token));

                await Task.WhenAll(langile1, langile2);
            }
            catch (TaskCanceledException)
            {
                MessageBox.Show("Lanaldia bertan behera utzi da.");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Errore bat gertatu da: {ex.Message}");
            }
        }

        // Langile bakoitzak egin behar duen lana simulatzen duen metodoa
        private async void LangileLana(string langilea, CancellationToken token)
        {
            // DatuakIkusiTabla-ko errenkada guztiak pasatzen ditugu
            foreach (DataGridViewRow row in DatuakIkusiTabla.Rows)
            {
                // Zeregina "Beteta" kolumna "Ez" bada (betetzeke dago)
                if (row.Cells["Beteta"].Value.ToString() == "Ez")
                {
                    // Zeregina langileari esleitzen diogu
                    row.Cells["Langilea"].Value = langilea;

                    // Zereginaren mota lortzen dugu
                    string eskaera = row.Cells["Eskaera"].Value.ToString();
                    int denbora = 0;

                    // Zeregin bakoitzaren denbora ezartzen dugu
                    if (eskaera == "Eskuoiak")
                    {
                        denbora = 2000;   // 2 segundo
                    }
                    else if (eskaera == "Izarak")
                    {
                        denbora = 3000;   // 3 segundo
                    }
                    else if (eskaera == "Zaborra")
                    {
                        denbora = 1000;   // 1 segundo
                    }
                    else if (eskaera == "Jatetxea")
                    {
                        denbora = 1000;   // 1 segundo
                    }

                    // Lanaren iraupena simulatzen dugu Task.Delay-rekin
                    try
                    {
                        await Task.Delay(denbora, token);  // Eginbidea eten nahi bada, token-a erabiltzen da
                    }
                    catch (OperationCanceledException)
                    {
                        // Eten bada, metodoa bukatu
                        return;
                    }

                    // Zeregina bukatuta, "Beteta" kolumna eguneratzen dugu
                    row.Cells["Beteta"].Value = "Bai";
                }
            }
        }

        // Lanaldia bukatu eta lanak gelditzeko botoia
        private void LanaldiaBukatuButton_Click(object sender, EventArgs e)
        {
            // Lanak gelditzeko cancel-a aktibatzen dugu
            if (_cancellationTokenSource != null)
            {
                _cancellationTokenSource.Cancel();  // Task guztiak bertan behera uzten dira
                MessageBox.Show("Lanaldia bukatu da, lanak gelditu dira.");
            }
        }

        // Datuak inportatzeko botoia
        private void DatuakInportatuButton_Click(object sender, EventArgs e)
        {
            // Ruta al archivo JAR de Java
            string rutaJava = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "..", "archivosJar", "Datuak.jar");

            if (File.Exists(rutaJava))
            {
                try
                {
                    // Configuración del proceso para ejecutar la clase de importación en Java
                    ProcessStartInfo startInfo = new ProcessStartInfo
                    {
                        FileName = "java",
                        Arguments = $"-cp \"{rutaJava}\" javaErronka.XMLimporter",
                        CreateNoWindow = true,
                        UseShellExecute = false
                    };

                    using (Process proceso = Process.Start(startInfo))
                    {
                        proceso.WaitForExit();  // Esperar a que el proceso de Java termine
                        MessageBox.Show("Datuak inportatu dira!", "Inportazioaren mezua", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        MessageBox.Show(" ");
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Errore bat gertatu da: {ex.Message}", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Programa Java ez dago aurkitua!", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Eskaera bat gehitzeko metodoa
        public void EskaeraGehitu(string eskaeraMota, int bezeroId, string langilea, string beteta)
        {
            // Fila berri bat sortzen dugu datuak jasotzeko
            DataGridViewRow row = new DataGridViewRow();

            // Datuak dituzten gelaxkak gehitzen ditugu errenkadan
            row.Cells.Add(new DataGridViewTextBoxCell() { Value = eskaeraMota });
            row.Cells.Add(new DataGridViewTextBoxCell() { Value = bezeroId });
            row.Cells.Add(new DataGridViewTextBoxCell() { Value = langilea });
            row.Cells.Add(new DataGridViewTextBoxCell() { Value = beteta });

            // Fila taulara gehitzen dugu
            DatuakIkusiTabla.Rows.Add(row);
        }

        // Mezua bezeroari bidaltzeko botoia
        private void MezuaBidaliButton_Click(object sender, EventArgs e)
        {
            AppBezero appBezeroForm = Application.OpenForms.OfType<AppBezero>().FirstOrDefault();

            if (appBezeroForm == null)
            {
                MessageBox.Show("No se encontró ninguna instancia de AppBezero abierta.");
                return;
            }

            // Iniciar el servidor en un hilo
            Thread zerbitzaria = new Thread(Zerbitzaria);
            zerbitzaria.Start();

            // Breve espera para asegurar que el servidor esté listo antes de que el cliente intente conectarse
            Thread.Sleep(500);

            // Iniciar el cliente en otro hilo
            Thread bezero = new Thread(() => Bezeroa(appBezeroForm));
            bezero.Start();
        }



        private void DatuakExportatuCSVbutton_Click(object sender, EventArgs e)
        {
            string rutaJava = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "..", "archivosJar", "Datuak.jar");

            if (File.Exists(rutaJava))
            {
                try
                {
                    ProcessStartInfo startInfo = new ProcessStartInfo
                    {
                        FileName = "java",
                        Arguments = $"-cp \"{rutaJava}\" javaErronka.CSVExporter ", // Ejecuta la clase de exportación con el formato como argumento
                        CreateNoWindow = true,
                        UseShellExecute = false,
                        RedirectStandardOutput = true,
                        RedirectStandardError = true // Captura errores de salida estándar
                    };

                    using (Process prozesua = Process.Start(startInfo))
                    {
                        string output = prozesua.StandardOutput.ReadToEnd();
                        string errors = prozesua.StandardError.ReadToEnd();
                        prozesua.WaitForExit();

                        // Comprobación de errores
                        if (!string.IsNullOrEmpty(errors))
                        {
                            MessageBox.Show($"Errore bat gertatu da: {errors}", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                        else if (output.Contains("Esportazioa burutu da!"))
                        {
                            MessageBox.Show($"Kanpinen datuak arrakastaz esportatu dira formatuan!",
                                            "Esportazioaren mezua", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else
                        {
                            MessageBox.Show("Esportazioak ez du emaitza espero zen moduan burutu.", "Esportazioaren mezua", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Errore bat gertatu da: {ex.Message}", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Programa Java ez dago aurkitua! Ruta: " + rutaJava, "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    

        private void DatuakExportatuXMLbutton_Click(object sender, EventArgs e)
        {
            string rutaJava = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "..", "archivosJar", "Datuak.jar");

            if (File.Exists(rutaJava))
            {
                try
                {
                    ProcessStartInfo startInfo = new ProcessStartInfo
                    {
                        FileName = "java",
                        Arguments = $"-cp \"{rutaJava}\" javaErronka.XMLImporter", // Ejecuta la clase de exportación con el formato como argumento
                        CreateNoWindow = true,
                        UseShellExecute = false,
                        RedirectStandardOutput = true,
                        RedirectStandardError = true // Captura errores de salida estándar
                    };

                    using (Process prozesua = Process.Start(startInfo))
                    {
                        string output = prozesua.StandardOutput.ReadToEnd();
                        string errors = prozesua.StandardError.ReadToEnd();
                        prozesua.WaitForExit();

                        // Comprobación de errores
                        if (!string.IsNullOrEmpty(errors))
                        {
                            MessageBox.Show($"Errore bat gertatu da: {errors}", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                        else if (output.Contains("Esportazioa burutu da!"))
                        {
                            MessageBox.Show($"Kanpinen datuak arrakastaz esportatu dira formatuan!",
                                            "Esportazioaren mezua", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else
                        {
                            MessageBox.Show("Esportazioak ez du emaitza espero zen moduan burutu.", "Esportazioaren mezua", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Errore bat gertatu da: {ex.Message}", "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Programa Java ez dago aurkitua! Ruta: " + rutaJava, "Errorea", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    

        


        public void Zerbitzaria()
        {
            try
            {
                using (var server = new NamedPipeServerStream("Erronka"))
                {
                    Console.WriteLine("[ZERBITZARIA] Datuak itxaroten...");
                    server.WaitForConnection(); // Espera la conexión del cliente

                    using (StreamWriter writer = new StreamWriter(server))
                    {
                        // Envía el mensaje desde el TextBox al cliente
                        string mezua = MezuaBidaliTextBox.Text;
                        Console.WriteLine("[ZERBITZARIA] Mezua bidaltzen: " + mezua);

                        writer.WriteLine(mezua);
                        writer.Flush();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("ERROR: zerbitzaria amatatzen - " + e.Message);
            }
        }


        public void Bezeroa(AppBezero appBezeroForm)
        {
            try
            {
                
                using (var client = new NamedPipeClientStream("Erronka"))
                {
                    client.Connect(); // Conexión al servidor
                    using (StreamReader reader = new StreamReader(client))
                    {
                        // Leer el mensaje del servidor y actualizar el TextBox en AppBezero
                        string mezua = reader.ReadLine();

                        // Llamar a MezuaJaso en el contexto de AppBezero
                        appBezeroForm.MezuaJaso(mezua);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("ERROR: bezeroa amatatzen - " + e.Message);
            }
        }



        private int IdBerriaGeneratu()
        {
            // Generamos un nuevo ID único de forma aleatoria
            Random rand = new Random();
            int IdBerria = rand.Next(azkenIda + 1, azkenIda + 1000); // Generamos un nuevo ID mayor al último

            // Actualizamos el último ID utilizado
            azkenIda = IdBerria;

            return IdBerria;
        }
    }

}
