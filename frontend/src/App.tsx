import React, { useEffect, useState } from 'react'
import { IoAdd } from 'react-icons/io5';
import { BiSearch } from 'react-icons/bi';
import axios from 'axios';
import { api } from './api/api'
import CardsSection from './components/CardsSection';


const App = () => {
  const [document, setDocument] = useState<any>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const handleUpload = (e: any) => {
    const formData = new FormData();
    if (e.target.files[0]) {
      formData.append('file', e.target.files[0]);
    } else {
      console.error('No file selected');
    }
    axios({
      method: "post",
      url: api + 'upload',
      data: formData,
      headers: { "Content-Type": "multipart/form-data" },
    })
      .then(function (response) {
        console.log(response);
        window.location.reload();
      })
      .catch(function (response) {
        console.log(response);
      });
  }

  const url = searchTerm ? api + `search?fileName=${searchTerm}` : api + 'get-documents'
  useEffect(() => {
    console.log(url)
    axios
      .get(url)
      .then((response) => {
        setDocument(response.data);
      })
      .catch((err) => console.error(err));
    console.log(searchTerm);
  }, [searchTerm]);
  return (
    <>
      <nav className='flex justify-between p-10 px-20 '>
        <form className='mr-10 w-[70%]'>
          <div className='relative'>
            <input type='text' onChange={(e) => setSearchTerm(e.target.value)} className='bg-[#302c2c] w-[100%] pl-10 pr-4 py-2 rounded-lg text-white  ' placeholder='search' />
            <div className="absolute  inset-y-0 left-0 pl-3 
                    flex items-center 
                    text-[#938c8a]
                    pointer-events-none">
              <BiSearch className='font-bold' />
            </div>
          </div>
        </form>
        <form className='w-[30%]' onChange={handleUpload} >
          <label>
            <input type="file" hidden />
            <div className="flex px-5 py-2 w-[100%] bg-white hover:bg-[#a8a4ac] font-semibold rounded-lg shadow text-[#302c2c] items-center justify-center cursor-pointer focus:outline-none">
              <IoAdd className='mr-2 font-bold' />
              <div>
                Add File
              </div>
            </div>
          </label>
        </form>
      </nav>
      <hr className='text-[#302c2c] font-bold' />

      <CardsSection document={document} />
    </>
  )
}
export default App;
