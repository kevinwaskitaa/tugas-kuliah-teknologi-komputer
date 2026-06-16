import cv2
import numpy as np

def segmentasi_ishihara(image_path, jumlah_kluster=3):
    # 1. Load Gambar (Input Citra Asli RGB) [cite: 157, 183]
    img = cv2.imread(image_path)
    if img is None:
        print("Gambar tidak ditemukan!")
        return

    # 2. Preprocessing: Ubah ke ruang warna HSV [cite: 82, 111]
    # HSV lebih dekat dengan penglihatan manusia dibanding RGB [cite: 82]
    hsv_img = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    
    # Ambil data piksel untuk clustering
    data_piksel = hsv_img.reshape((-1, 3))
    data_piksel = np.float32(data_piksel)

    # 3. Algoritma K-Means Clustering [cite: 190, 191]
    # Mengelompokkan piksel berdasarkan kemiripan warna [cite: 189, 190]
    criteria = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, 10, 1.0)
    _, label, center = cv2.kmeans(data_piksel, jumlah_kluster, None, criteria, 10, cv2.KMEANS_RANDOM_CENTERS)

    # Konversi kembali ke gambar 8-bit
    center = np.uint8(center)
    res = center[label.flatten()]
    hasil_segmentasi = res.reshape((img.shape))

    # Tampilkan Hasil [cite: 185, 187]
    cv2.imshow('Citra Asli RGB', img)
    cv2.imshow('Hasil Segmentasi Warna K-Means', hasil_segmentasi)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# Jalankan fungsi (ganti dengan nama file gambar Ishihara kamu)
# segmentasi_ishihara('ishihara_plate.png')