
namespace AppErronka
{
    partial class AppBezero
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
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
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            EskareaComboBox = new ComboBox();
            EskaeraEginButton = new Button();
            label1 = new Label();
            BideojokoaHasiButton = new Button();
            CheckOutButton = new Button();
            textBox1 = new TextBox();
            SuspendLayout();
            // 
            // EskareaComboBox
            // 
            EskareaComboBox.FormattingEnabled = true;
            EskareaComboBox.Location = new Point(12, 120);
            EskareaComboBox.Name = "EskareaComboBox";
            EskareaComboBox.Size = new Size(121, 23);
            EskareaComboBox.TabIndex = 0;
            // 
            // EskaeraEginButton
            // 
            EskaeraEginButton.Location = new Point(150, 120);
            EskaeraEginButton.Name = "EskaeraEginButton";
            EskaeraEginButton.Size = new Size(114, 23);
            EskaeraEginButton.TabIndex = 1;
            EskaeraEginButton.Text = "Eskaera egin";
            EskaeraEginButton.UseVisualStyleBackColor = true;
            EskaeraEginButton.Click += EskaeraEginButton_Click;
            // 
            // label1
            // 
            label1.BackColor = Color.White;
            label1.Font = new Font("Segoe UI", 9F, FontStyle.Regular, GraphicsUnit.Point, 0);
            label1.Location = new Point(12, 9);
            label1.Name = "label1";
            label1.Size = new Size(368, 23);
            label1.TabIndex = 6;
            label1.TextAlign = ContentAlignment.TopCenter;
            // 
            // BideojokoaHasiButton
            // 
            BideojokoaHasiButton.Location = new Point(12, 269);
            BideojokoaHasiButton.Name = "BideojokoaHasiButton";
            BideojokoaHasiButton.Size = new Size(121, 23);
            BideojokoaHasiButton.TabIndex = 3;
            BideojokoaHasiButton.Text = "Bideojokoa hasi";
            BideojokoaHasiButton.UseVisualStyleBackColor = true;
            BideojokoaHasiButton.Click += BideojokoaHasiButton_Click;
            // 
            // CheckOutButton
            // 
            CheckOutButton.Location = new Point(12, 240);
            CheckOutButton.Name = "CheckOutButton";
            CheckOutButton.Size = new Size(121, 23);
            CheckOutButton.TabIndex = 5;
            CheckOutButton.Text = "Check out";
            CheckOutButton.UseVisualStyleBackColor = true;
            CheckOutButton.Click += CheckOutButton_Click;
            // 
            // textBox1
            // 
            textBox1.AcceptsReturn = true;
            textBox1.AcceptsTab = true;
            textBox1.AllowDrop = true;
            textBox1.Location = new Point(150, 159);
            textBox1.Multiline = true;
            textBox1.Name = "textBox1";
            textBox1.ReadOnly = true;
            textBox1.Size = new Size(230, 192);
            textBox1.TabIndex = 7;
            // 
            // AppBezero
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.DarkOliveGreen;
            ClientSize = new Size(392, 363);
            Controls.Add(textBox1);
            Controls.Add(CheckOutButton);
            Controls.Add(BideojokoaHasiButton);
            Controls.Add(label1);
            Controls.Add(EskaeraEginButton);
            Controls.Add(EskareaComboBox);
            Name = "AppBezero";
            Text = "AppBezero";
            ResumeLayout(false);
            PerformLayout();
        }



        #endregion

        private ComboBox EskareaComboBox;
        private Button EskaeraEginButton;
        private Label label1;
        private Button BideojokoaHasiButton;
        private Button CheckOutButton;
        private TextBox textBox1;
    }
}