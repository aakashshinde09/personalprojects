import { useState } from 'react'
import './App.css'
import AddRoom from './components/room/AddRoom'
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "/node_modules/bootstrap/dist/js/bootstrap.min.js"
import ExistingRooms from './components/room/ExistingRooms'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <AddRoom />
      
    </>
  )
}

export default App
