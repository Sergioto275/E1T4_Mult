
namespace AppErronka
{
    partial class AdminApp
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            comboBox1 = new ComboBox();
            MezuaBidaliTextBox = new TextBox();
            DatuakInportatuButton = new Button();
            MezuaBidaliButton = new Button();
            LanaldiaBukatuButton = new Button();
            LanaldiaHasiButton = new Button();
            DatuakIkusiTabla = new DataGridView();
            Eskaera = new DataGridViewTextBoxColumn();
            BezeroID = new DataGridViewTextBoxColumn();
            Langilea = new DataGridViewTextBoxColumn();
            Beteta = new DataGridViewTextBoxColumn();
            BezeroBerriaButton = new Button();
            DatuakExportatuCSVbutton = new Button();
            DatuakExportatuXMLbutton = new Button();
            textBox1 = new TextBox();
            ostatuMotaComboBox = new ComboBox();
            label1 = new Label();
            label2 = new Label();
            ((System.ComponentModel.ISupportInitialize)DatuakIkusiTabla).BeginInit();
            SuspendLayout();
            // 
            // comboBox1
            // 
            comboBox1.FormattingEnabled = true;
            comboBox1.Location = new Point(377, 227);
            comboBox1.Name = "comboBox1";
            comboBox1.Size = new Size(121, 23);
            comboBox1.TabIndex = 17;
            // 
            // MezuaBidaliTextBox
            // 
            MezuaBidaliTextBox.Location = new Point(377, 36);
            MezuaBidaliTextBox.Multiline = true;
            MezuaBidaliTextBox.Name = "MezuaBidaliTextBox";
            MezuaBidaliTextBox.Size = new Size(311, 184);
            MezuaBidaliTextBox.TabIndex = 16;
            // 
            // DatuakInportatuButton
            // 
            DatuakInportatuButton.Location = new Point(54, 94);
            DatuakInportatuButton.Name = "DatuakInportatuButton";
            DatuakInportatuButton.Size = new Size(116, 23);
            DatuakInportatuButton.TabIndex = 14;
            DatuakInportatuButton.Text = "Datuak Inportatu";
            DatuakInportatuButton.UseVisualStyleBackColor = true;
            DatuakInportatuButton.Click += DatuakInportatuButton_Click;
            // 
            // MezuaBidaliButton
            // 
            MezuaBidaliButton.Location = new Point(572, 226);
            MezuaBidaliButton.Name = "MezuaBidaliButton";
            MezuaBidaliButton.Size = new Size(116, 23);
            MezuaBidaliButton.TabIndex = 13;
            MezuaBidaliButton.Text = "Mezua bidali";
            MezuaBidaliButton.UseVisualStyleBackColor = true;
            MezuaBidaliButton.Click += MezuaBidaliButton_Click;
            // 
            // LanaldiaBukatuButton
            // 
            LanaldiaBukatuButton.Location = new Point(54, 65);
            LanaldiaBukatuButton.Name = "LanaldiaBukatuButton";
            LanaldiaBukatuButton.Size = new Size(116, 23);
            LanaldiaBukatuButton.TabIndex = 12;
            LanaldiaBukatuButton.Text = "Lanaldia bukatu";
            LanaldiaBukatuButton.UseVisualStyleBackColor = true;
            LanaldiaBukatuButton.Click += LanaldiaBukatuButton_Click;
            // 
            // LanaldiaHasiButton
            // 
            LanaldiaHasiButton.Location = new Point(54, 36);
            LanaldiaHasiButton.Name = "LanaldiaHasiButton";
            LanaldiaHasiButton.Size = new Size(116, 23);
            LanaldiaHasiButton.TabIndex = 11;
            LanaldiaHasiButton.Text = "Lanaldia hasi";
            LanaldiaHasiButton.UseVisualStyleBackColor = true;
            LanaldiaHasiButton.Click += LanaldiaHasiButton_Click;
            // 
            // DatuakIkusiTabla
            // 
            DatuakIkusiTabla.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            DatuakIkusiTabla.Columns.AddRange(new DataGridViewColumn[] { Eskaera, BezeroID, Langilea, Beteta });
            DatuakIkusiTabla.Location = new Point(54, 285);
            DatuakIkusiTabla.Name = "DatuakIkusiTabla";
            DatuakIkusiTabla.Size = new Size(634, 211);
            DatuakIkusiTabla.TabIndex = 10;
            // 
            // Eskaera
            // 
            Eskaera.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            Eskaera.HeaderText = "Eskaera";
            Eskaera.Name = "Eskaera";
            // 
            // BezeroID
            // 
            BezeroID.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            BezeroID.HeaderText = "BezeroID";
            BezeroID.Name = "BezeroID";
            // 
            // Langilea
            // 
            Langilea.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            Langilea.HeaderText = "Langilea";
            Langilea.Name = "Langilea";
            // 
            // Beteta
            // 
            Beteta.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            Beteta.HeaderText = "Beteta";
            Beteta.Name = "Beteta";
            // 
            // BezeroBerriaButton
            // 
            BezeroBerriaButton.Location = new Point(221, 167);
            BezeroBerriaButton.Name = "BezeroBerriaButton";
            BezeroBerriaButton.Size = new Size(116, 23);
            BezeroBerriaButton.TabIndex = 9;
            BezeroBerriaButton.Text = "Bezero berria";
            BezeroBerriaButton.UseVisualStyleBackColor = true;
            BezeroBerriaButton.Click += BezeroBerriaButton_Click;
            // 
            // DatuakExportatuCSVbutton
            // 
            DatuakExportatuCSVbutton.Location = new Point(201, 83);
            DatuakExportatuCSVbutton.Name = "DatuakExportatuCSVbutton";
            DatuakExportatuCSVbutton.Size = new Size(116, 40);
            DatuakExportatuCSVbutton.TabIndex = 18;
            DatuakExportatuCSVbutton.Text = "Datuak exportatu CSV-an";
            DatuakExportatuCSVbutton.UseVisualStyleBackColor = true;
            DatuakExportatuCSVbutton.Click += DatuakExportatuCSVbutton_Click;
            // 
            // DatuakExportatuXMLbutton
            // 
            DatuakExportatuXMLbutton.Location = new Point(201, 36);
            DatuakExportatuXMLbutton.Name = "DatuakExportatuXMLbutton";
            DatuakExportatuXMLbutton.Size = new Size(116, 41);
            DatuakExportatuXMLbutton.TabIndex = 20;
            DatuakExportatuXMLbutton.Text = "Datuak Exportatuk XML-an";
            DatuakExportatuXMLbutton.UseVisualStyleBackColor = true;
            DatuakExportatuXMLbutton.Click += DatuakExportatuXMLbutton_Click;
            // 
            // textBox1
            // 
            textBox1.Location = new Point(94, 168);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(121, 23);
            textBox1.TabIndex = 21;
            // 
            // ostatuMotaComboBox
            // 
            ostatuMotaComboBox.FormattingEnabled = true;
            ostatuMotaComboBox.Location = new Point(94, 197);
            ostatuMotaComboBox.Name = "ostatuMotaComboBox";
            ostatuMotaComboBox.Size = new Size(121, 23);
            ostatuMotaComboBox.TabIndex = 22;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.BackColor = Color.White;
            label1.Location = new Point(38, 171);
            label1.Name = "label1";
            label1.Size = new Size(34, 15);
            label1.TabIndex = 23;
            label1.Text = "Izena";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.BackColor = Color.White;
            label2.Location = new Point(38, 200);
            label2.Name = "label2";
            label2.Size = new Size(48, 15);
            label2.TabIndex = 24;
            label2.Text = "Ostatua";
            // 
            // AdminApp
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.DarkOliveGreen;
            ClientSize = new Size(733, 556);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(ostatuMotaComboBox);
            Controls.Add(textBox1);
            Controls.Add(DatuakExportatuXMLbutton);
            Controls.Add(DatuakExportatuCSVbutton);
            Controls.Add(comboBox1);
            Controls.Add(MezuaBidaliTextBox);
            Controls.Add(DatuakInportatuButton);
            Controls.Add(MezuaBidaliButton);
            Controls.Add(LanaldiaBukatuButton);
            Controls.Add(LanaldiaHasiButton);
            Controls.Add(DatuakIkusiTabla);
            Controls.Add(BezeroBerriaButton);
            Name = "AdminApp";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)DatuakIkusiTabla).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }



        #endregion

        private ComboBox comboBox1;
        private TextBox MezuaBidaliTextBox;
        private Button DatuakInportatuButton;
        private Button MezuaBidaliButton;
        private Button LanaldiaBukatuButton;
        private Button LanaldiaHasiButton;
        private DataGridView DatuakIkusiTabla;
        private DataGridViewTextBoxColumn Eskaera;
        private DataGridViewTextBoxColumn BezeroID;
        private DataGridViewTextBoxColumn Langilea;
        private DataGridViewTextBoxColumn Beteta;
        private Button BezeroBerriaButton;
        private Button DatuakExportatuCSVbutton;
        private Button DatuakExportatuXMLbutton;
        private TextBox textBox1;
        private ComboBox ostatuMotaComboBox;
        private Label label1;
        private Label label2;
    }
}
