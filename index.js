const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const dotenv = require('dotenv');

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

const { MongoMemoryServer } = require('mongodb-memory-server');

let MONGO_URI = process.env.MONGO_URI;

const startDatabase = async () => {
  try {
    if (!MONGO_URI || MONGO_URI.includes('localhost')) {
      console.log('Starting persistent Local MongoDB via MemoryServer...');
      const fs = require('fs');
      const path = require('path');
      const dbPath = path.join(__dirname, 'mongodb_data');
      if (!fs.existsSync(dbPath)) fs.mkdirSync(dbPath);
      
      const mongod = await MongoMemoryServer.create({ 
          instance: { dbPath, storageEngine: 'wiredTiger' } 
      });
      MONGO_URI = mongod.getUri();
      console.log(`Database permanently persisting to: ${dbPath}`);
    }
    await mongoose.connect(MONGO_URI);
    console.log('Connected to MongoDB');
  } catch (err) {
    console.error('MongoDB connection error:', err);
  }
};
startDatabase();

const authRoutes = require('./routes/auth');
const categoryRoutes = require('./routes/categories');
const questionRoutes = require('./routes/questions');
const quizRoutes = require('./routes/quiz');

app.use('/api/auth', authRoutes);
app.use('/api/categories', categoryRoutes);
app.use('/api/questions', questionRoutes);
app.use('/api/quiz', quizRoutes);
app.use('/api/ai', require('./routes/ai'));

app.get('/api/health', (req, res) => res.json({ status: 'ok' }));

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
