from flask import Flask,render_template,send_file

app = Flask(__name__)

@app.route("/")
def home():
    return render_template('index.html')

@app.route("/cv")
def cv():
    return send_file('static/cv.pdf')


if __name__ == "__main__":
    app.run()