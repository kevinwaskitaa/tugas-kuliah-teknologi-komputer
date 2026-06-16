# Mengimpor library OpenCV untuk mengakses kamera dan menampilkan video
import cv2

# Mengimpor MediaPipe untuk deteksi tangan
import mediapipe as mp

# Mengimpor PyAutoGUI untuk mengirim tombol keyboard ke game
import pyautogui


# Mengambil modul Hands dari MediaPipe
mp_hands = mp.solutions.hands

# Modul untuk menggambar landmark tangan di layar
mp_draw = mp.solutions.drawing_utils


# Membuat objek deteksi tangan
hands = mp_hands.Hands(
    min_detection_confidence=0.7,   # tingkat kepercayaan minimal agar tangan dianggap terdeteksi
    min_tracking_confidence=0.7     # tingkat kepercayaan pelacakan tangan antar frame
)


# Membuka webcam (0 = kamera utama laptop)
cap = cv2.VideoCapture(0)


# Variabel untuk menyimpan posisi telunjuk sebelumnya
prev_x = None
prev_y = None


# Loop utama program agar kamera terus berjalan
while True:

    # Membaca frame dari webcam
    ret, frame = cap.read()

    # Membalik gambar agar seperti cermin
    frame = cv2.flip(frame,1)

    # Mengubah warna gambar dari BGR ke RGB
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    # Memproses frame untuk mendeteksi tangan
    results = hands.process(rgb)


    # Jika ada tangan terdeteksi
    if results.multi_hand_landmarks:

        # Loop setiap tangan yang terdeteksi
        for hand in results.multi_hand_landmarks:

            # Mengambil ukuran frame
            h, w, _ = frame.shape

            # Landmark 8 adalah ujung jari telunjuk
            index = hand.landmark[8]

            # Mengubah koordinat telunjuk dari skala 0-1 menjadi pixel layar
            x = int(index.x * w)
            y = int(index.y * h)


            # Jika sudah ada posisi sebelumnya
            if prev_x is not None:

                # Menghitung pergerakan telunjuk
                dx = x - prev_x
                dy = y - prev_y


                # Jika bergerak ke kanan
                if dx > 40:
                    pyautogui.press("right")  # tekan tombol panah kanan
                    print("RIGHT")            # tampilkan di terminal


                # Jika bergerak ke kiri
                if dx < -40:
                    pyautogui.press("left")   # tekan tombol panah kiri
                    print("LEFT")


                # Jika tangan naik
                if dy < -40:
                    pyautogui.press("up")     # karakter lompat
                    print("JUMP")


                # Jika tangan turun
                if dy > 40:
                    pyautogui.press("down")   # karakter roll
                    print("ROLL")


            # Simpan posisi telunjuk sekarang untuk frame berikutnya
            prev_x = x
            prev_y = y


            # Menggambar garis kerangka tangan di layar
            mp_draw.draw_landmarks(
                frame,                        # frame kamera
                hand,                         # landmark tangan
                mp_hands.HAND_CONNECTIONS     # garis penghubung antar titik
            )


    # Menampilkan jendela kamera
    cv2.imshow("Gesture Controller", frame)


    # Jika tombol ESC ditekan maka program berhenti
    if cv2.waitKey(1) & 0xFF == 27:
        break


# Menutup kamera
cap.release()

# Menutup semua jendela OpenCV
cv2.destroyAllWindows()