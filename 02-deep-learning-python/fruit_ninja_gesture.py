import cv2
import mediapipe as mp
import random

# mediapipe hands
mp_hands = mp.solutions.hands
mp_draw = mp.solutions.drawing_utils

hands = mp_hands.Hands()

# buka webcam
cap = cv2.VideoCapture(0)

# posisi buah
fruit_x = random.randint(100,500)
fruit_y = random.randint(100,400)

score = 0

while True:

    ret, frame = cap.read()
    frame = cv2.flip(frame,1)

    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    results = hands.process(rgb)

    if results.multi_hand_landmarks:

        for hand in results.multi_hand_landmarks:

            h,w,_ = frame.shape

            # telunjuk
            index = hand.landmark[8]

            x = int(index.x*w)
            y = int(index.y*h)

            # gambar pedang (telunjuk)
            cv2.circle(frame,(x,y),10,(0,255,255),-1)

            # cek jika menyentuh buah
            if abs(x-fruit_x) < 40 and abs(y-fruit_y) < 40:

                score += 1

                fruit_x = random.randint(100,500)
                fruit_y = random.randint(100,400)

            mp_draw.draw_landmarks(frame,hand,mp_hands.HAND_CONNECTIONS)

    # gambar buah
    cv2.circle(frame,(fruit_x,fruit_y),20,(0,0,255),-1)

    # tampilkan skor
    cv2.putText(frame,"Score: "+str(score),(10,40),
                cv2.FONT_HERSHEY_SIMPLEX,1,(255,255,255),2)

    cv2.imshow("Fruit Ninja Gesture",frame)

    if cv2.waitKey(1) & 0xFF == 27:
        break

cap.release()
cv2.destroyAllWindows()