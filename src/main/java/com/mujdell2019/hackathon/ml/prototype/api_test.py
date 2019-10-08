import json
from flask import Flask, request

app = Flask(__name__)

@app.route('/')
def index():
    return "Hello, World!"

@app.route('/main1',methods = ['POST'])
def post():
	key1 = request.args.get('key1')
	key2 = request.args.get('key2')
	return {key1 : key2}

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port = "8080")