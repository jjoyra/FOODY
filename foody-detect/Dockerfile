# 베이스 이미지로 파이썬 3.8을 사용
FROM python:3.8-slim-buster

# 작업 디렉터리 설정
WORKDIR /app

# 의존성 파일들을 컨테이너에 복사
COPY requirements.txt requirements.txt

# 의존성 설치
RUN pip install --upgrade pip && \
    pip install -r requirements.txt

# 모든 파일을 컨테이너에 복사
COPY . .

# Flask 실행을 위한 환경 변수 설정
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0

# 5000 포트로 서비스를 실행
EXPOSE 5000

# Flask 앱 실행
CMD ["flask", "run"]