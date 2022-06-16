/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipetik;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chynthia
 */
//public class FPegawai extends javax.swing.JInternalFrame implements internal
public class FPegawai extends javax.swing.JInternalFrame{
    koneksi k = new koneksi();
    String a, A,B,jenkel;
    String no,dkp,c,d,e,f,g,s,t,u,v,w,x,y,z, klik;
    String h,i,j,m,l,photo;
    private byte[] foto;
    private Image image;
    File file;
    BufferedImage bi;
    File hapud;
    JFileChooser chooser = new JFileChooser();
    /**
     * Creates new form fPegawai
     */
    private DefaultTableModel model;
    public PreparedStatement stmt;
    
    public FPegawai() {
        initComponents();
        setCode();
        showData();
    }
    
     public void getImage() throws IOException{
        
        
        chooser.setDialogTitle("Pilih foto anda");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files","jpg","png","jpeg"));
        chooser.setAcceptAllFileFilterUsed(false);
        
        int returnval = chooser.showOpenDialog(this);
        readfile();
    }
     
     void readfile(){
         try{
                file = chooser.getSelectedFile();
                bi = ImageIO.read(file);
                
               lFoto.setIcon(new ImageIcon(bi.getScaledInstance(lFoto.getWidth(),lFoto.getHeight(), Image.SCALE_SMOOTH)));
                
            } catch(IOException e){
                System.out.println("Error"+e);
            }
     }
     
      public void Copy(String from, String to){
        
        try{
            FileInputStream fis = new FileInputStream(from);
            FileOutputStream fos = new FileOutputStream(to);
            int f ;
            while((f=fis.read())!=-1){
                fos.write(f);
            }                            
            }catch(IOException e){
                    System.out.println("Error"+e);
        }
    }
      
      public void setCode(){
        try {
            k.query = "select MAX(right(kode_pegawai,4)) as no from tbpegawai";
            k.getData();
            while(k.rs.next()){
                //Cek untuk record yang paling awal di tb batik
                if(k.rs.first()==false){
                    //menampilkan kedalam textfield kodebatik
                    tfKode.setText("PG000001");
                }else{
                    //cek untuk record yang paling akhir di tb batik
                    k.rs.last();
                    //digit terakhir kodepemasok
                    int id = k.rs.getInt(1)+1;
                    //konversi ke string
                    String no = String.valueOf(id);
                    //cek untuk angka digit terakhir
                    switch(no.length()){
                        case 1:
                            no = "00000" + id;
                            break;
                        case 2:
                            no = "0000" + id;
                            break;
                        case 3:
                            no = "000" + id;
                            break;
                        case 4:
                            no = "00" + id;
                            break;
                        case 5:
                            no = "0" + id;
                            break;
                        case 6:
                            no = "" + id;
                            break;
                    }
                    tfKode.setText("PG"+no);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error "+ex +" FPegawai.setCode");
        }
    }
      
     void cariData(){
        try {
            
            tablePegawai.setDefaultEditor(Object.class, null);
            Object[] coloum={"Kode Pegawai","Nama Pegawai","Jenis Kelamin","Tanggal Lahir","Alamat","No Handphone"};
            model=new DefaultTableModel(coloum,0);
            tablePegawai.setModel(model);
            k.query="select * from tbpegawai "
                    + "where kode_pegawai like '%"+tfCari.getText()+
                    "%' or nama_pegawai like '%"+tfCari.getText()+
                    "%' or jk like '%"+tfCari.getText()+
                    "%' or tgl_lahir like '%"+tfCari.getText()+
                    "%' or alamat like '%"+tfCari.getText()+
                    "%' or hp like '%"+tfCari.getText()+
                    "%' or hak_akses like '%"+tfCari.getText()+
                    "%'";
            k.getData();
            while(k.rs.next()){
                c=k.rs.getString("kode_pegawai");
                d=k.rs.getString("nama_pegawai");
                e=k.rs.getString("jk");
                f=k.rs.getString("tgl_lahir");
                g=k.rs.getString("alamat");
                s=k.rs.getString("hp");
                w=k.rs.getString("hak_akses");
                
                String[] data={c,d,e,f,g,s,w};
                model.addRow(data);
                
            }
        } catch (SQLException ex) {
            System.out.println("error"+ex);
        }
    }
    
     void clearForm(){
        tfKode.setText("");
        tfNama.setText("");
        buttonGroup1.clearSelection();
        dcTanggal.setDate(null);
        taAlamat.setText("");
        tfNo.setText("");
        tfUser.setText("");
        tfPass.setText("");
        lFoto.setIcon(null);
        lFoto.setText("");
        cbHak.setSelectedIndex(0);
            setCode();
    }
    
    public void showData(){
        try {
            tablePegawai.setDefaultEditor(Object.class, null);
            Object[] coloum={"Kode Pegawai","Nama Pegawai","Jenis Kelamin","Tanggal Lahir","Alamat","No Handphone"};
            model=new DefaultTableModel(coloum,0);
            tablePegawai.setModel(model);
            k.query="select * from tbpegawai";
            k.getData();
            while(k.rs.next()){
                c=k.rs.getString("kode_pegawai");
                d=k.rs.getString("nama_pegawai");
                e=k.rs.getString("jk");
                f=k.rs.getString("tgl_lahir");
                g=k.rs.getString("alamat");
                s=k.rs.getString("hp");
                t=k.rs.getString("foto");
                u=k.rs.getString("username");
                v=k.rs.getString("password");
                w=k.rs.getString("hak_akses");
                
                String[] data={c,d,e,f,g,s,t,u,v,w};
                model.addRow(data);
            }
            k.rs.close();
        } catch (SQLException ex) {
            System.out.println("error"+ex);
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lFoto = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfKode = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        rbPria = new javax.swing.JRadioButton();
        rbWanita = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        tfNo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbHak = new javax.swing.JComboBox();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tfCari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePegawai = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        tfPass = new javax.swing.JPasswordField();
        dcTanggal = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));

        lFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btnBrowse.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnBrowse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari.png"))); // NOI18N
        btnBrowse.setBorderPainted(false);
        btnBrowse.setContentAreaFilled(false);
        btnBrowse.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/carion.png"))); // NOI18N
        btnBrowse.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cari.png"))); // NOI18N
        btnBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBrowseMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel3.setText("Kode Pegawai");

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel4.setText("Nama Pegawai");

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setText("Jenis Kelamin");

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setText("Tanggal Lahir");

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel7.setText("Alamat");

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setText("No. HP");

        tfKode.setEditable(false);
        tfKode.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfKode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfKodeMouseClicked(evt);
            }
        });
        tfKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKodeActionPerformed(evt);
            }
        });

        tfNama.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        buttonGroup1.add(rbPria);
        rbPria.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        rbPria.setText("Pria");
        rbPria.setContentAreaFilled(false);

        buttonGroup1.add(rbWanita);
        rbWanita.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        rbWanita.setText("Wanita");
        rbWanita.setContentAreaFilled(false);

        taAlamat.setColumns(20);
        taAlamat.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        taAlamat.setRows(5);
        jScrollPane1.setViewportView(taAlamat);

        tfNo.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNoKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("Username");

        tfUser.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfUserKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel10.setText("Password");

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setText("Hak Akses");

        cbHak.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        cbHak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "operator", "kasir" }));

        btnSimpan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.setBorderPainted(false);
        btnSimpan.setContentAreaFilled(false);
        btnSimpan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpanon.png"))); // NOI18N
        btnSimpan.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sunting.png"))); // NOI18N
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/suntingon.png"))); // NOI18N
        btnEdit.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sunting.png"))); // NOI18N
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.setBorderPainted(false);
        btnHapus.setContentAreaFilled(false);
        btnHapus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapuson.png"))); // NOI18N
        btnHapus.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkan.png"))); // NOI18N
        btnRefresh.setBorderPainted(false);
        btnRefresh.setContentAreaFilled(false);
        btnRefresh.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkanon.png"))); // NOI18N
        btnRefresh.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bersihkan.png"))); // NOI18N
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel12.setText("CARI DATA PEGAWAI");

        tfCari.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfCariMouseClicked(evt);
            }
        });
        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariKeyReleased(evt);
            }
        });

        tablePegawai.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tablePegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode Pegawai", "Nama Pegawai", "Jenis Kelamin", "Tanggal Lahir", "Alamat", "No. HP"
            }
        ));
        tablePegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePegawai);

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 0));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DATA PEGAWAI.png"))); // NOI18N

        tfPass.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        dcTanggal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(lFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(btnBrowse)))
                                .addGap(87, 87, 87)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel4)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel5)))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfNama, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rbPria)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rbWanita))
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(tfNo, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(dcTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(46, 46, 46)
                                        .addComponent(tfKode, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel9)
                                                .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11)
                                                        .addComponent(cbHak, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(tfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16)))
                                        .addGap(82, 82, 82))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnSimpan)
                                        .addGap(31, 31, 31)
                                        .addComponent(btnEdit))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(817, 817, 817)))
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbWanita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(rbPria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBrowse))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbHak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEdit)
                                    .addComponent(btnSimpan))
                                .addGap(12, 12, 12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tfKodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfKodeMouseClicked
      
    }//GEN-LAST:event_tfKodeMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
       showData();
       clearForm();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        int j;
        j=JOptionPane.showConfirmDialog(null, "Anda Ingin Menghapus Pegawai dengan Kode Pegawai "+tfKode.getText()+"?","Info",JOptionPane.YES_NO_OPTION);
        if(j==JOptionPane.YES_OPTION){
            try {
                k.query="delete from tbpegawai where kode_pegawai='"+tfKode.getText()+"'";
                k.setData();
                k.ps.executeUpdate();
                hapud.delete();
                
                 JOptionPane.showMessageDialog(null, "Data telah dihapus","Info",JOptionPane.INFORMATION_MESSAGE);
                
                clearForm();
                showData();
            } catch (SQLException ex) {
                System.out.println("error"+ ex);
            }
        }
        clearForm();
        showData();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void tfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyReleased
         try {
            tablePegawai.setDefaultEditor(Object.class, null);
            Object[] coloum={"Kode Pegawai","Nama Pegawai","Jenis Kelamin","Tanggal Lahir","Alamat","No Handphone"};
            model=new DefaultTableModel(coloum,0);
            tablePegawai.setModel(model);
            k.query="select * from tbpegawai "
                    + "where kode_pegawai like '%"+tfCari.getText()+
                    "%' or nama_pegawai like '%"+tfCari.getText()+
                    "%' or jk like '%"+tfCari.getText()+
                    "%' or tgl_lahir like '%"+tfCari.getText()+
                    "%' or alamat like '%"+tfCari.getText()+
                    "%' or hp like '%"+tfCari.getText()+
                    "%' or hak_akses like '%"+tfCari.getText()+
                    "%'";
            k.getData();
            while(k.rs.next()){
                c=k.rs.getString("kode_pegawai");
                d=k.rs.getString("nama_pegawai");
                e=k.rs.getString("jk");
                f=k.rs.getString("tgl_lahir");
                g=k.rs.getString("alamat");
                s=k.rs.getString("hp");
                w=k.rs.getString("hak_akses");
                
                String[] data={c,d,e,f,g,s,w};
                model.addRow(data);
                
            }
        } catch (SQLException ex) {
            System.out.println("error"+ex);
        }
    }//GEN-LAST:event_tfCariKeyReleased

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        try {
            k.query="update tbpegawai set kode_pegawai=?, nama_pegawai=?, jk=?, tgl_lahir=?, alamat=?, hp=?,foto=?,username=?,password=?,hak_akses=? where kode_pegawai='"+tfKode.getText()+"'";
            k.setData();
            
                    
                    k.ps.setString(1, String.valueOf(tfKode.getText()));
                    k.ps.setString(2, String.valueOf(tfNama.getText()));
                    
                    
                    if (rbPria.isSelected()) {
                        k.ps.setString(3,"Pria");
                    } else {
                        k.ps.setString(3,"Wanita");
                    }
                    
                                        
                    java.util.Date utilSD = dcTanggal.getDate();
                    java.sql.Date sqlSD = new java.sql.Date(utilSD.getTime());
                    k.ps.setString(4, String.valueOf(sqlSD));

                    k.ps.setString(5, String.valueOf(taAlamat.getText()));
                    k.ps.setString(6, String.valueOf(tfNo.getText()));
                    readfile();
                    if(file==null){
                        photo = hapud.getName();
                    }
                    else{
                        hapud.delete();
                        photo="src\\foto\\"+file.getName();
                    }
                    
                    k.ps.setString(7, photo);
                    Copy(file.getAbsolutePath(), photo);
                    
                    k.ps.setString(8, String.valueOf(tfUser.getText()));
                    k.ps.setString(9, String.valueOf(tfPass.getText()));

                    k.ps.setString(10, String.valueOf(cbHak.getSelectedItem().toString()));
                    k.ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Data telah diubah","Info",JOptionPane.INFORMATION_MESSAGE);

                    
            clearForm();
            showData();
        } catch (SQLException ex) {
            System.out.println("error"+ex);
        }
        clearForm();
        showData();        
    }//GEN-LAST:event_btnEditMouseClicked

    private void tfNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNoKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_tfNoKeyTyped

    private void tablePegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePegawaiMouseClicked
        try {
            int row = tablePegawai.getSelectedRow();
            klik= tablePegawai.getModel().getValueAt(row, 0).toString();
            k.query = "select * from tbpegawai where "
                    + "kode_pegawai='" + klik + "'";    
            k.getData();
            k.rs.first();
                x = k.rs.getString("kode_pegawai");
                tfKode.setText(x);
                tfKode.enable(false);

                y = k.rs.getString("nama_pegawai");
                tfNama.setText(y);

                z = k.rs.getString("jk");
                if (z.equals("Pria")) {
                    rbPria.setSelected(true);
                } else {
                    rbWanita.setSelected(true);
                }
                
                h = k.rs.getString("tgl_lahir");
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(h);
                dcTanggal.setDate(date);
               
               
                s = k.rs.getString("alamat");
                taAlamat.setText(s);
                
                t = k.rs.getString("hp");
                tfNo.setText(t);
                
                u = k.rs.getString("username");
                tfUser.setText(u);
                
                v = k.rs.getString("password");
                tfPass.setText(v);
                
                x = k.rs.getString("foto");
                file = new File(x);
                bi=ImageIO.read(file);
                lFoto.setIcon(new ImageIcon(bi.getScaledInstance(lFoto.getWidth(),lFoto.getHeight(), Image.SCALE_SMOOTH)));
                
                
                w = k.rs.getString("hak_akses");
                cbHak.setSelectedItem(w);
                
                String ha="D:\\UPI\\SEMESTER 4\\PEMROGRAMAN BERORIENTASI OBJEK\\NETBEANS\\SiPeTik\\src\\foto"+file.getName();
                hapud=new File(ha);
            
        } catch (SQLException ex) {
            Logger.getLogger(FPemasok.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FPegawai.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        showData();
    }//GEN-LAST:event_tablePegawaiMouseClicked

    private void btnBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBrowseMouseClicked
         try {
            getImage();
        } catch (IOException ex) {
            Logger.getLogger(FPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBrowseMouseClicked

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
         if(tfNama.equals("")||buttonGroup1.equals("")||dcTanggal.equals("")||taAlamat.equals("") ||tfNo.equals("")||lFoto.equals("")|| file==null|| tfUser.equals("")||cbHak.equals("")){
            JOptionPane.showMessageDialog(null,"Gagal menambahkan data baru","Peringatan",JOptionPane.ERROR_MESSAGE);
        }
        else{    
            try {
                k.query="select*from tbpegawai where kode_pegawai='"+String.valueOf(tfKode.getText())+"'";
                k.getData();
                
                while(k.rs.next()){
                    dkp=k.rs.getString("kode_pegawai");
                }
                if(dkp==String.valueOf(tfKode.getText())){
                    JOptionPane.showMessageDialog(null, "Gagal Menambahkan, Data sudah ada","Peringatan",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    k.query = "insert into tbpegawai values (?,?,?,?,?,?,?,?,?,?)";
                    k.setData();
                    String kode_pegawai = tfKode.getText();
                    String nama_pegawai = tfNama.getText();
                    String tgl_lahir = dcTanggal.getDateFormatString();
                    String alamat = taAlamat.getText();
                    String hp = tfNo.getText();
                    
                    k.ps.setString(1, String.valueOf(tfKode.getText()));
                    k.ps.setString(2, String.valueOf(tfNama.getText()));

                    if (rbPria.isSelected()) {
                        k.ps.setString(3,"Pria");
                    } else {
                        k.ps.setString(3,"Wanita");
                    }
                                        
                    java.util.Date utilSD = dcTanggal.getDate();
                    java.sql.Date sqlSD = new java.sql.Date(utilSD.getTime());
                    k.ps.setString(4, String.valueOf(sqlSD));

                    k.ps.setString(5, String.valueOf(taAlamat.getText()));
                    k.ps.setString(6, String.valueOf(tfNo.getText()));
                    
                                       
                    k.ps.setString(7, "src\\foto\\"+file.getName());
                    Copy(file.getAbsolutePath(), "src\\foto\\"+file.getName());
                    
                    k.ps.setString(8, String.valueOf(tfUser.getText()));
                    k.ps.setString(9, String.valueOf(tfPass.getText()));

                    k.ps.setString(10, String.valueOf(cbHak.getSelectedItem().toString()));
                    k.ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Data telah ditambahkan","Info",JOptionPane.INFORMATION_MESSAGE);

                    model.addRow(new Object[]{
                        kode_pegawai,nama_pegawai, jenkel,tgl_lahir,alamat,hp
                    });
                    showData();
                    clearForm();
                }
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Username sudah ada!","Peringatan",JOptionPane.INFORMATION_MESSAGE);
            }
    }
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void tfKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKodeActionPerformed
 
    }//GEN-LAST:event_tfKodeActionPerformed

    private void tfCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfCariMouseClicked
        cariData();
    }//GEN-LAST:event_tfCariMouseClicked

    private void tfUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUserKeyReleased
        
    }//GEN-LAST:event_tfUserKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbHak;
    private com.toedter.calendar.JDateChooser dcTanggal;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lFoto;
    private javax.swing.JRadioButton rbPria;
    private javax.swing.JRadioButton rbWanita;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTable tablePegawai;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfKode;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNo;
    private javax.swing.JPasswordField tfPass;
    private javax.swing.JTextField tfUser;
    // End of variables declaration//GEN-END:variables
}
