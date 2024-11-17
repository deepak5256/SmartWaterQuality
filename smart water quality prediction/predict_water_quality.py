import sys
import pickle
import numpy as np

def load_model():
    with open('water_quality_model.pkl', 'rb') as file:
        model = pickle.load(file)
    return model

def predict(pH, hardness):
    model = load_model()
    features = np.array([[pH, hardness]])
    prediction = model.predict(features)
    return "Safe" if prediction[0] == 1 else "Not Safe"

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python3 predict_water_quality.py <pH> <Hardness>")
        sys.exit(1)
    
    pH = float(sys.argv[1])
    hardness = float(sys.argv[2])
    
    result = predict(pH, hardness)
    print(result)

