# import library kamera
import cv2

# import mediapipe untuk deteksi tangan
import mediapipe as mp

# import pyautogui untuk mengontrol mouse
import pyautogui

# modul hands mediapipe
mp_hands = mp.solutions.hands

# modul menggambar titik tangan
mp_draw = mp.solutions.drawing_utils

# membuat objek deteksi tangan
hands = mp_hands.Hands()

# membuka webcam
cap = cv2.VideoCapture(0)

# mendapatkan ukuran layar komputer
screen_w, screen_h = pyautogui.size()

while True:

    # membaca frame kamera
    ret, frame = cap.read()

    # membalik frame agar seperti cermin
    frame = cv2.flip(frame,1)

    # konversi warna BGR ke RGB
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    # proses deteksi tangan
    results = hands.process(rgb)

    # jika tangan terdeteksi
    if results.multi_hand_landmarks:

        for hand in results.multi_hand_landmarks:

            # ukuran frame kamera
            h, w, _ = frame.shape

            # landmark 8 = ujung telunjuk
            index = hand.landmark[8]

            # ubah koordinat ke pixel
            x = int(index.x * w)
            y = int(index.y * h)

            # ubah koordinat kamera ke koordinat layar
            screen_x = screen_w * index.x
            screen_y = screen_h * index.y

            # gerakkan mouse
            pyautogui.moveTo(screen_x, screen_y)

            # gambar titik di telunjuk
            cv2.circle(frame,(x,y),10,(0,255,255),-1)

            # gambar kerangka tangan
            mp_draw.draw_landmarks(frame,hand,mp_hands.HAND_CONNECTIONS)

    # tampilkan kamera
    cv2.imshow("Gesture Mouse",frame)

    # tekan ESC untuk keluar
    if cv2.waitKey(1) & 0xFF == 27:
        break

cap.release()
cv2.destroyAllWindows()