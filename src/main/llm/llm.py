import os
from embedchain import App

# Set the OpenAI API key
os.environ["OPENAI_API_KEY"] = "sk-xxx"

from openai import OpenAI

client = OpenAI()

response = client.audio.speech.create(
    model="tts-1",
    voice="alloy",
    input="Hello world! This is a streaming test.",
)

response.stream_to_file("output.mp3")

# Initialize the App with the API key set
bot =  App.from_config(config_path="config.json")

# Add directory
bot.add("C:\\Users\\sivas\\OneDrive\\Desktop\\New folder", data_type="directory")

query = "How are politics in Belgium and Sri Lanka similar?"

bot.query(query)

from flask import Flask, request, jsonify

app = Flask(_name_)
@app.route('/query', methods=['POST'])
def query():
    data = request.json
    query= data.get('query')
    if not query:
        return jsonify({'error': 'Query is required.'}), 400
    response = bot.query(query)
    return jsonify({'response': response})

if _name_ == '_main_':
    app.run()