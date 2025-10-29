package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LichChieu {
	private String maLichChieu;
    private Phim phim;
    private Phong phong;
    private LocalDate ngayChieu;
    private LocalDateTime gioBatDau;
    private LocalDateTime gioKetThuc;

    public LichChieu() {}

    public LichChieu(String maLichChieu, Phim phim, Phong phong, 
                     LocalDate ngayChieu, LocalDateTime gioBatDau, LocalDateTime gioKetThuc) {
        this.maLichChieu = maLichChieu;
        this.phim = phim;
        this.phong = phong;
        this.ngayChieu = ngayChieu;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    public String getMaLichChieu() { return maLichChieu; }
    public void setMaLichChieu(String maLichChieu) { this.maLichChieu = maLichChieu; }
    
    public Phim getPhim() { return phim; }
    public void setPhim(Phim phim) { this.phim = phim; }
    
    public Phong getPhong() { return phong; }
    public void setPhong(Phong phong) { this.phong = phong; }
    
    public LocalDate getNgayChieu() { return ngayChieu; }
    public void setNgayChieu(LocalDate ngayChieu) { this.ngayChieu = ngayChieu; }
    
    public LocalDateTime getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(LocalDateTime gioBatDau) { this.gioBatDau = gioBatDau; }
    
    public LocalDateTime getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(LocalDateTime gioKetThuc) { this.gioKetThuc = gioKetThuc; }
}
