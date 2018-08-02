pragma solidity ^0.4.4;


contract studentRecords {

  address recordsAdmin;

  mapping ( bytes32 => notarizedImage) notarizedImages; // this allows to look up notarizedImages by their SHA256notaryHash
  bytes32[] imagesByNotaryHash; // this is like a whitepages of all images, by SHA256notaryHash

  mapping ( address => Student ) Students;   // this allows to look up Users by their ethereum address
  address[] studentsByAddress;  // this is like a whitepages of all users, by ethereum address

  struct notarizedImage {
    string imageURL;
    uint timeStamp;
  }

  struct Student {
    string name;
    bytes32 aline1;
    bytes32 aline2;
    bytes32 aline3;
    bytes32 sid;
    bytes32 studentImage;
  }

  constructor (){ 
    recordsAdmin = msg.sender;  // set the admin, so they can remove bad users or images if needed
  }

  modifier onlyAdmin() {
      if (msg.sender != recordsAdmin)
        throw;
     
      _;
  }

   function removeUser(address badUser) onlyAdmin returns (bool success) {
    delete Students[badUser];
    return true;
  }

  function removeImage(bytes32 badImage) onlyAdmin returns (bool success) {
    delete notarizedImages[badImage];
    return true;
  }

  function registerNewStudent(string name, bytes32 aline1, bytes32 aline2, bytes32 aline3,bytes32 sid) returns (bool success) {
    address NewAddress = msg.sender;
   
    if(bytes(Students[msg.sender].name).length == 0 && bytes(name).length != 0){
      Students[NewAddress].name = name;
      Students[NewAddress].aline1 = aline1;
      Students[NewAddress].aline2 = aline2;
      Students[NewAddress].aline3 = aline3;
      Students[NewAddress].sid = sid;
      studentsByAddress.push(NewAddress);  // adds an entry for this user to the user 'whitepages'
      return true;
    } else {
      return false; // either handle was null, or a user with this handle already existed
    }
  }

  function addImageToStudent(string imageURL, bytes32 SHA256notaryHash) returns (bool success) {
    address NewAddress = msg.sender;
    if(bytes(Students[NewAddress].name).length != 0){ // Check account created
      if(bytes(imageURL).length != 0){   // ) {  // Unsure how to check a null byte32
        // prevent users from fighting over sha->image listings 
        if(bytes(notarizedImages[SHA256notaryHash].imageURL).length == 0) {
          imagesByNotaryHash.push(SHA256notaryHash); // adds entry for this image to our image whitepages
        }
        notarizedImages[SHA256notaryHash].imageURL = imageURL;
        notarizedImages[SHA256notaryHash].timeStamp = block.timestamp; //Timestamp
        Students[NewAddress].studentImage =SHA256notaryHash; // Add student image
        return true;
      } else {
        return false; // either imageURL or SHA256notaryHash was null, couldn't store image
      }
      return true;
    } else {
      return false; // user didn't have an account yet, couldn't store image
    }
  }

  function getStudents() constant returns (address[]) { return studentsByAddress; }

  function getStudent(address userAddress) constant returns (string,bytes32,bytes32,bytes32,bytes32,bytes32) {
    return (Students[userAddress].name,Students[userAddress].aline1,Students[userAddress].aline2,
    Students[userAddress].aline3,Students[userAddress].studentImage,Students[userAddress].sid);
  }

  function getAllImages() constant returns (bytes32[]) { return imagesByNotaryHash; }

  function getStudentImage(address userAddress) constant returns (bytes32) { return Students[userAddress].studentImage; }

  function getImage(bytes32 SHA256notaryHash) constant returns (string,uint) {
    return (notarizedImages[SHA256notaryHash].imageURL,notarizedImages[SHA256notaryHash].timeStamp);
  }

}