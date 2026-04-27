import { gameData } from './data.js';
import express from 'express';

const app = express()
const port = 3001

app.get('/', (req, res) => {
  res.send(`Run app in ${port}!`)
})


app.get('/game/:game/config', (req, res) => {
  const { game } = req.params;

  const config = gameData[game];

  if (!config) {
    return res.status(404).send({ 
      error: "Game not found" 
    });
  }

  res.send({
    status: true,
    data: config
  });
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
